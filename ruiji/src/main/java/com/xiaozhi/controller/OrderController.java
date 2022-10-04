package com.xiaozhi.controller;


import com.xiaozhi.pojo.Orders;
import com.xiaozhi.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20232
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(value = " OrderController", tags = {"订单控制器"})
public class OrderController {

    @PostMapping("/submit")
    public Result<String> submitGoods(@RequestBody Orders orders) {
        Long addressBookId = orders.getAddressBookId();
        log.info("addressBookId:{}",addressBookId);
        return Result.success("");
    }
}
