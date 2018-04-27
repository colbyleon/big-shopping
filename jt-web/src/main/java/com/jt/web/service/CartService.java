package com.jt.web.service;

import com.jt.web.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findCartListByUserId(Long userId);

    void saveCart(Cart cart);

    void updateCartNum(Long userId, Long itemId, Integer num);

    void deleteCart(Long itemId, Long userId);
}
