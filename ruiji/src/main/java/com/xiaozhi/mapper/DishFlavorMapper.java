package com.xiaozhi.mapper;

import com.xiaozhi.pojo.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.DishFlavor
*/

@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




