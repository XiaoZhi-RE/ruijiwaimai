package com.xiaozhi.mapper;

import com.xiaozhi.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.Dish
*/


@Repository
public interface DishMapper extends BaseMapper<Dish> {

}




