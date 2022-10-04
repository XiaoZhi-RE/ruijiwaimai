package com.xiaozhi.mapper;

import com.xiaozhi.pojo.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.OrderDetail
*/

@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




