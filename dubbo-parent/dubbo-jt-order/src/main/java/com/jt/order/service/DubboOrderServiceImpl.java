package com.jt.order.service;

import com.jt.dubbo.service.DubboOrderService;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DubboOrderServiceImpl implements DubboOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 1. 将order对象通过rabbitMQ实现数据的传递
     * 2. 需要返回orderId
     */
    @Override
    public String saveOrder(Order order) {
        // 准备orderId
        String orderId = order.getUserId() + "" + System.currentTimeMillis();
        // 将order订单实现入库
        order.setOrderId(orderId);
        String routingKey = "save.order";
        // 表示消息的发送
        rabbitTemplate.convertAndSend(routingKey, order);
        System.out.println("已经将订单加入了消息队列");
        return orderId;
    }

    /**
     * 根据订单id查询订单对象
     * <p>
     * 使用mybatis实现多表关联查询
     */
    @Override
    public Order findOrderById(String orderId) {
        Order order = orderMapper.selectByOrderId(orderId);
        if (order == null) return null;

        System.out.println(order);
        System.out.println(order.getOrderShipping());
        System.out.println(order.getOrderItems());
        return order;
    }
}
