package com.jt.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    // http://order.jt.com/order/create
    @RequestMapping("/create")
    @ResponseBody
    public SysResult saveOrder(String orderJSON) {
        try {
            Order order = objectMapper.readValue(orderJSON, Order.class);
            String orderId = orderService.saveOrder(order);

            if (!StringUtils.isEmpty(orderId))
                return SysResult.oK(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"创建订单失败");
    }

    // URL	http://order.jt.com/order/query/81425700649826
    @RequestMapping("/query/{orderId}")
    @ResponseBody
    public Order findOrderByid(@PathVariable String orderId) {
        Order order = orderService.findOrderById(orderId);
        return order;
    }
}
