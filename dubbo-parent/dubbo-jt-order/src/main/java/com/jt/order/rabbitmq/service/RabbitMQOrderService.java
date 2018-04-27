package com.jt.order.rabbitmq.service;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class RabbitMQOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    Logger logger = Logger.getLogger("订单");

    public void saveOrder(Order order) {
        String  orderId = order.getOrderId();

        order.setStatus(1);
        order.setCreated(new Date());
        order.setUpdated(order.getCreated());
        orderMapper.insert(order);
        logger.info("订单入库成功");

        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(order.getCreated());
        orderShipping.setUpdated(order.getCreated());
        orderShippingMapper.insert(orderShipping);
        logger.info("订单物流入库成功");

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId);
            orderItem.setCreated(order.getCreated());
            orderItem.setUpdated(order.getCreated());
            orderItemMapper.insert(orderItem);
        }
        logger.info("订单商品入库成功");
    }


}
