package com.xiaozhi.mapper;

import com.xiaozhi.pojo.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.ShoppingCart
*/


@Repository
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




