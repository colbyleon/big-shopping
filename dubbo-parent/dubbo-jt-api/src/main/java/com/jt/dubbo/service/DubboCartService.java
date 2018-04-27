package com.jt.dubbo.service;

import com.jt.dubbo.pojo.Cart;

import java.util.List;

public interface DubboCartService {
    List<Cart> findCartListByUserId(Long userId);

    void saveCart(Cart cart);

    void updateCartNum(Long userId, Long itemId, Integer num);

    void deleteCart(Long userId, Long itemId);
}
