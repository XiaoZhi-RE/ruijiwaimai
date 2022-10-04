package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.Orders;
import com.xiaozhi.service.OrdersService;
import com.xiaozhi.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 20232
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-07-12 10:33:12
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




