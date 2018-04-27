package com.jt.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private HttpClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 请求http://order.jt.com/order/create
     */
    @Override
    public String saveOrder(Order order) {
        // 1. 定义远程访问的uri
        String uri = "http://order.jt.com/order/create";
        try {
            // 2. 将order转换成JSON数据
            String orderJSON = objectMapper.writeValueAsString(order);
            // 3. 通过post提交实现参数的传递
            Map<String, String> params = new HashMap<>();
            params.put("orderJSON", orderJSON);
            String resultJSON = clientService.doPost(uri, params, "utf-8");
            // 4. 解析返回的数据OrderId
            SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
            if (sysResult.getStatus() == 200) {
                return (String) sysResult.getData();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order findOrderByid(String orderId) {
        // 1. 定义远程访问的uri
        String uri = "http://order.jt.com/order/query/"+orderId;
        // 2. 发送远程访问
        try {
            String orderJSON = clientService.doGet(uri);
            Order order = objectMapper.readValue(orderJSON, Order.class);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
