package com.xiaozhi.mapper;

import com.xiaozhi.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.Orders
*/


@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

}




