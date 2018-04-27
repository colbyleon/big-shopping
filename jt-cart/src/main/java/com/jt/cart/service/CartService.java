package com.jt.cart.service;


import com.jt.cart.pojo.Cart;

import java.util.List;

public interface CartService {

    List<Cart> findCartListByUserId(Long userId);

    void saveCart(Cart cart);

    void updateCartNum(Long userId, Long itemId, Integer num);

    void deleteCart(Long userId, Long itemId);
}
