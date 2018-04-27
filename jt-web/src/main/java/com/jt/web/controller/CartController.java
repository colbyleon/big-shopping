package com.jt.web.controller;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 1. 获取用户id
     * 2. 发送请求获取数据
     * 3. 转向购物车列页面
     * @return
     */
    @RequestMapping("/show")
    public String findCartListByUserId(Model model){
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * http://www.jt.com/cart/add/562379.html
     */
    @RequestMapping("/add/{itemId}")
    public String saveCart(@PathVariable Long itemId,Cart cart) {
        Long userId = UserThreadLocal.get().getId();
        cart.setItemId(itemId);
        cart.setUserId(userId);
        cartService.saveCart(cart);
        return "redirect:/cart/show.html";
    }

    /**
     *  http://www.jt.com/service/cart/update/num/562379/3
     *  更新购物车的数量
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
        try {
            Long userId = UserThreadLocal.get().getId();
            cartService.updateCartNum(userId,itemId,num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "购物车修改失败");
    }

    /**
     * http://www.jt.com/cart/delete/1474391964.html
     * 删除购物车
     */
    @RequestMapping("/delete/{itemId}")
    public String  deleteCart(@PathVariable Long itemId){
        Long userId = UserThreadLocal.get().getId();
        cartService.deleteCart(itemId, userId);
        return "redirect:/cart/show.html";
    }
}
