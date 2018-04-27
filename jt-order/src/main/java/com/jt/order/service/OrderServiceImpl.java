package com.jt.order.service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    Logger logger = Logger.getLogger("订单");
    @Override
    public String saveOrder(Order order) {
        // 准备orderId
        String orderId = order.getUserId() +""+ System.currentTimeMillis();
        // 将order订单实现入库
        order.setOrderId(orderId);
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
        return orderId;
    }

    /**
     * 根据订单id查询订单对象
     */
    @Override
    public Order findOrderById(String orderId) {
        // 1. 先查出订单
        Order order = orderMapper.selectByPrimaryKey(orderId);
        // 2. 查出订单物流
        OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
        // 3. 查出订单商品
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        List<OrderItem> orderItems = orderItemMapper.select(orderItem);
        // 4. 封装订单并返回
        order.setOrderItems(orderItems);
        order.setOrderShipping(orderShipping);
        return order;
    }
}
