package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.OrderDetail;
import com.xiaozhi.service.OrderDetailService;
import com.xiaozhi.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 20232
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-07-12 10:33:12
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




