package com.jt.web.controller;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.service.DubboCartService;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private DubboOrderService orderService;

    @Autowired
    private DubboCartService cartService;

    // http://www.jt.com/order/create.html
    @RequestMapping("/create")
    public String create(Model model) {
        User user = UserThreadLocal.get();
        List<Cart> carts = cartService.findCartListByUserId(user.getId());
        model.addAttribute("carts", carts);
        return "order-cart";
    }

    // http://www.jt.com/service/order/submit

    /**
     * 新增订单
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order) {
        try {
            Long userId = UserThreadLocal.get().getId();
            order.setUserId(userId);
            String orderId = orderService.saveOrder(order);
            if (!StringUtils.isEmpty(orderId))
                return SysResult.oK(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "订单新增失败");
    }

    /**
     * 结算页面
     * http://www.jt.com/order/success.html?id=151524127108773
     */
    @RequestMapping("/success")
    public String success(@RequestParam("id") String orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        model.addAttribute("order", order);
        return "success";
    }
}
