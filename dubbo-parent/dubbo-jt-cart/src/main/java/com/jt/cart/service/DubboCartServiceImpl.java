package com.jt.cart.service;

import com.jt.cart.mapper.CartMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired
    private CartMapper cartMapper;


    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        List<Cart> cartList = cartMapper.select(cart);
        System.out.println();
        return cartList;
    }

    /**
     * 购物车新增的业务逻辑
     * 1. 根据userId 和 itemId 查询数据库，是否有该记录
     *      a) 如果有，将购物的数量进行累加
     *      b) 如没有，则执行insert操作
     */
    @Override
    public void saveCart(Cart cart) {
        Cart cartDB = cartMapper.findCartByUI(cart);
        if (cartDB == null) {
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cartMapper.insert(cart);
            System.out.println("购物车新增成功");
        }else {
            // 数据不为空时
            int num = cart.getNum() + cartDB.getNum();
            cartDB.setNum(num);
            cartDB.setUpdated(new Date());
            cartMapper.updateByPrimaryKeySelective(cartDB);
            System.out.println("更新购物车成功");
        }
    }

    @Override
    public void updateCartNum(Long userId, Long itemId, Integer num) {
        Cart cart = new Cart();
        cart.setItemId(itemId);
        cart.setUserId(userId);
        cart.setNum(num);
        cart.setUpdated(new Date());
        cartMapper.updateCartNum(cart);
    }

    @Override
    public void deleteCart(Long userId, Long itemId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(itemId);
        cartMapper.delete(cart);
        System.out.println("删除购物车成功");
    }
}
