package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.common.BaseContext;
import com.xiaozhi.mapper.ShoppingCartMapper;
import com.xiaozhi.pojo.ShoppingCart;
import com.xiaozhi.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 20232
 * @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        //线程得到userID
        Long currentId = BaseContext.getCurrentId();
        log.warn("userId:" + currentId);
        //设置userid为线程获取的id
        shoppingCart.setUserId(currentId);
        //得到dishId
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dishId != null) {
            //构建查询条件，根据dishId和userId查询
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId).eq(ShoppingCart::getUserId, currentId);
        } else {
            //构建查询条件，根据SetmealId和userId查询
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId()).eq(ShoppingCart::getUserId, currentId);
        }
        //查询是否添加过菜品或者套餐
        ShoppingCart shoppingCart1 = baseMapper.selectOne(lambdaQueryWrapper);
        if (shoppingCart1 != null) {
            //获取数量
            Integer number = shoppingCart1.getNumber();
            //设置数量，数据库查询到id
            shoppingCart1.setNumber(number + 1);
            //更新数据库
            baseMapper.updateById(shoppingCart1);
        } else {
            shoppingCart.setNumber(1);
            baseMapper.insert(shoppingCart);
            shoppingCart1 = shoppingCart;
        }
        return shoppingCart1;
    }


    @Transactional
    @Override
    public ShoppingCart subShoppingCart(ShoppingCart shoppingCart) {

        //获取用户id
        Long userId = BaseContext.getCurrentId();
        //获取dishId
        Long dishId = shoppingCart.getDishId();
        //获取SetmealId
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //不等于null
        if (dishId != null) {
            log.info("正在删除套餐数量：disId:{}", dishId);
            //根据user和dishId查询购物车信息
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId).eq(ShoppingCart::getDishId, dishId);
            //查询一条购物车记录
            ShoppingCart oneRecords = baseMapper.selectOne(shoppingCartLambdaQueryWrapper);
            //判断是否为空和number是否为0
            if (oneRecords != null && oneRecords.getNumber() != 0) {
                Integer number = oneRecords.getNumber();
                //数量减1
                oneRecords.setNumber(number - 1);
                if (oneRecords.getNumber() != 0) {
                    UpdateWrapper<ShoppingCart> updateWrapper = new UpdateWrapper<>();
                    //update容器
                    updateWrapper.set(oneRecords.getNumber() != null, "number", oneRecords.getNumber()).eq("user_id", oneRecords.getUserId());
                    //根据的是购物车id更新，要根据用户的id进行添加
                    baseMapper.update(oneRecords, updateWrapper);
                    log.info("oneRecords:{}", oneRecords);
                } else {
                    log.warn("正在删除:{}", oneRecords.getId());
                    //如果数量为0，则删除数据库中的这条记录
                    baseMapper.deleteById(oneRecords);
                    //shoppingCartService.removeById(oneRecords);
                }
            }
            return oneRecords;
        } else {
            log.info("正在删除套餐的id:{}", setmealId);
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId).eq(ShoppingCart::getSetmealId, setmealId);
            ShoppingCart oneRecords = baseMapper.selectOne(shoppingCartLambdaQueryWrapper);
            if (oneRecords != null && oneRecords.getNumber() != 0) {
                Integer number = oneRecords.getNumber();
                oneRecords.setNumber(number - 1);
                if (oneRecords.getNumber() != 0) {
                    UpdateWrapper<ShoppingCart> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.set(oneRecords.getNumber() != null, "number", oneRecords.getNumber()).eq("user_id", oneRecords.getUserId());
                    baseMapper.update(oneRecords, updateWrapper);
                } else {
                    //如果数量为0，则删除数据库中的这条记录
                    baseMapper.deleteById(oneRecords);
                }
            }
            return oneRecords;
        }
    }
}




