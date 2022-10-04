package com.xiaozhi.service;

import com.xiaozhi.pojo.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 20232
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2022-07-12 10:33:12
*/
public interface ShoppingCartService extends IService<ShoppingCart> {

    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart subShoppingCart(ShoppingCart shoppingCart);
}
