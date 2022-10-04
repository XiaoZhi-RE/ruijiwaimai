package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.DishFlavor;
import com.xiaozhi.service.DishFlavorService;
import com.xiaozhi.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 20232
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-07-12 10:33:12
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




