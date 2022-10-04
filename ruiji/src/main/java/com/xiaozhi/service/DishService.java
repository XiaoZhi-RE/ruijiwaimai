package com.xiaozhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.dto.DishDto;
import com.xiaozhi.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 20232
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-07-12 10:33:12
*/
public interface DishService extends IService<Dish> {

    IPage getDishList(Page<Dish> page1, String dishName);

    DishDto getDisInfo(Long id);

    void updateByDishId(DishDto dishDto);

    void saveWithFlavor(DishDto dishDto);

    List<Dish> selectByIds(List<Long> ids);

    void removeDish(List<Long> ids);
}
