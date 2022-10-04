package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.common.CustomException;
import com.xiaozhi.dto.DishDto;
import com.xiaozhi.mapper.DishMapper;
import com.xiaozhi.pojo.Dish;
import com.xiaozhi.pojo.DishFlavor;
import com.xiaozhi.service.DishFlavorService;
import com.xiaozhi.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20232
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 分页查询，获取dishList
     *
     * @param page1
     * @param dishName
     * @return
     */
    @Override
    public IPage getDishList(Page<Dish> page1, String dishName) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        设置查询条件,判断是否为空
        queryWrapper.like(!StringUtils.isEmpty(dishName), Dish::getName, dishName);
        //      根据更新时间进行排序
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        return baseMapper.selectPage(page1, queryWrapper);
    }

    @Override
    public DishDto getDisInfo(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
        dishDto.setFlavors(dishFlavorList);

        return dishDto;
    }

    @Override
    public void updateByDishId(DishDto dishDto) {
        //        更新dish表菜品修改信息
        this.updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //        查询条件，DELETE * from dish_flavor where id = ?
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        // 删除对应的口味数据
        dishFlavorService.remove(lambdaQueryWrapper);
        //        通过disDto获取口味列表
        List<DishFlavor> list = dishDto.getFlavors();
        list = list.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        //        批量保存
        dishFlavorService.saveBatch(list);
    }

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public List<Dish> selectByIds(List<Long> ids) {
        List<Dish> dishList = baseMapper.selectBatchIds(ids);
        return dishList;
    }

    @Override
    public void removeDish(List<Long> ids) {

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId, ids).eq(Dish::getStatus, 1);

        long count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("菜品正在售卖，无法删除");
        }

        this.removeBatchByIds(ids);
    }
}




