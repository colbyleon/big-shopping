package com.jt.web.service;

import com.jt.web.pojo.Order;

public interface OrderService {
    String saveOrder(Order order);

    Order findOrderByid(String orderId);
}
