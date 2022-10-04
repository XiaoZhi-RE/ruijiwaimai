package com.xiaozhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20232
 * RestController请求控制器
 */
@RestController
public class TestController {


    /**
     *请求/hello，测试工程是否可以启动
     * @return String
     */
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!....";
    }
}
