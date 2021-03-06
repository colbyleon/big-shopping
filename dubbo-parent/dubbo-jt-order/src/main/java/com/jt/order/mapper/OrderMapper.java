package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Order;

import java.util.Date;

public interface OrderMapper extends SysMapper<Order> {
    void updateStatus(Date agoDate);

    Order selectByOrderId(String orderId);
}
