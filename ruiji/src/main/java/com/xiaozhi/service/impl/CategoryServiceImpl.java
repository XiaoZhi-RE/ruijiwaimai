package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.common.CustomException;
import com.xiaozhi.mapper.CategoryMapper;
import com.xiaozhi.pojo.Category;
import com.xiaozhi.pojo.Dish;
import com.xiaozhi.pojo.Setmeal;
import com.xiaozhi.service.CategoryService;
import com.xiaozhi.service.DishService;
import com.xiaozhi.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 20232
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 分页查询
     *
     * @param pages
     * @return
     */
    @Override
    public IPage selectCategoryList(Page<Category> pages) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 根据数据库的sort字段实现降序排序
        queryWrapper.orderByAsc(Category::getSort);
        return baseMapper.selectPage(pages, queryWrapper);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {

        //        查询分类是否关联了菜品
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        //        创建查询条件,这里查询的是CategoryId，而不是dish的id
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        long countDish = dishService.count(dishQueryWrapper);
        log.info("conuntdish" + countDish);
        //       判断是否关联
        if (countDish > 0) {
            /*抛出自定义异常*/
            throw new CustomException("无法删除该分类，已关联其他菜品");
        }

        //        查询分类是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        if (countSetmeal > 0) {
            throw new CustomException("无法删除该分类，已关联其他套餐");
            /*抛出自定义异常*/
        }
        //        调用父类的removeById方法
        super.removeById(id);


    }

    @Override
    public List<Category> selectByType(Integer type) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断请求参数是否存在
        if (type != null) {
            lambdaQueryWrapper.eq(Category::getType, type);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}




