package com.jt.web.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private HttpClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        String uri = "http://cart.jt.com/cart/query/"+userId;
        try {
            String jsonData = clientService.doGet(uri);
            SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
            List<Cart> cartList = (List<Cart>) sysResult.getData();
            return cartList;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过跨域的请求实现购物车的新增
     * 1. 定位usi
     * 2. 封装需要传递的数据
     * 3. 发出POST请求
     * 4. 获取返回值的数据
     */
    @Override
    public void saveCart(Cart cart) {
        String uri = "http://cart.jt.com/cart/save";
        Map<String, String> params = new HashMap<>();
        params.put("userId", cart.getUserId()+ "");
        params.put("itemId", cart.getItemId()+ "");
        params.put("itemTitle", cart.getItemTitle());
        params.put("itemImage", cart.getItemImage()+ "");
        params.put("itemPrice", cart.getItemPrice()+ "");
        params.put("num", cart.getNum() + "");

        try {
            clientService.doPost(uri, params, "utf-8");
            System.out.println("跨域调用成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCartNum(Long userId, Long itemId, Integer num) {
        String uri = "http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
        try {
            clientService.doGet(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCart(Long itemId, Long userId) {
        String uri = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
        try {
            clientService.doGet(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
