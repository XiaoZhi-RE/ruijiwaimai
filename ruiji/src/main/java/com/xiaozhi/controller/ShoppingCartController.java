package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaozhi.common.BaseContext;
import com.xiaozhi.pojo.ShoppingCart;
import com.xiaozhi.service.ShoppingCartService;
import com.xiaozhi.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 20232
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
@Api(value = "ShoppingCartController", tags = {"购物车控制器"})
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> getList() {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return Result.success(list);
    }


    /**
     * 添加商品
     *
     * @param shoppingCart 购物车实体类
     * @return 购物车信息
     */
    @PostMapping("/add")
    public Result<ShoppingCart> addGoods(@RequestBody ShoppingCart shoppingCart) {
        log.info("Cart:{}", shoppingCart);
        ShoppingCart shoppingCartInfo = shoppingCartService.add(shoppingCart);
        return Result.success(shoppingCartInfo);
    }


    /**
     * 添加商品到购物车
     *
     * @param shoppingCart 购物车实体类
     * @return 一条购物车信息
     */
    @PostMapping("/sub")
    public Result<ShoppingCart> subGoods(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartRecords = shoppingCartService.subShoppingCart(shoppingCart);
        return Result.success(shoppingCartRecords);
    }

    /**
     * 清空购物车
     *
     * @return 错误信息
     */
    @DeleteMapping("/clean")
    public Result<String> cleanShoppingCart() {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(lambdaQueryWrapper);
        return Result.success("删除购物车成功");
    }
}
