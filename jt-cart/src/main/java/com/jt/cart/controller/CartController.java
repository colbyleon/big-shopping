package com.jt.cart.controller;


import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //http://cart.jt.com/cart/query/{userId}
    @RequestMapping("query/{userId}")
    @ResponseBody
    public SysResult findCartListByUserId(@PathVariable Long userId){
        try {
            List<Cart> cartList = cartService.findCartListByUserId(userId);
            return SysResult.oK(cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "购物信息查询失败");
    }

    // http://cart.jt.com/cart/save
    @RequestMapping("/save")
    public SysResult saveCart(Cart cart){
        try {
            cartService.saveCart(cart);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "新增购物车失败");
    }

    /**
     * 修改购物车商品的数量
     */
    @RequestMapping("/update/num/{userId}/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(@PathVariable Long userId,
                                   @PathVariable Long itemId,
                                   @PathVariable Integer num){
        try {
            cartService.updateCartNum(userId,itemId,num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "数量更新失败");
    }

    /**
     * 删除购物车
     */
    @RequestMapping("/delete/{userId}/{itemId}")
    @ResponseBody
    public SysResult deleteCart(@PathVariable Long userId,@PathVariable Long itemId) {
        try {
            cartService.deleteCart(userId, itemId);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "删除失败");

    }
}
