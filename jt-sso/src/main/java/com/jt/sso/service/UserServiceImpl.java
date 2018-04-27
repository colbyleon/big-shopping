package com.jt.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 思路：
     * 根据给定的信息，经过分析，查询记录总数最为合适
     */
    @Override
    public Boolean findCheckUser(String param, String column) {
        int count = userMapper.findCheckUser(param, column);
        return count != 0;
    }

    @Override
    public String saveUser(User user) {
        if (StringUtils.isEmpty(user.getUsername())
                || StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getPhone()))
            return null;
        // 将密码加密
        String md5Hex = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(md5Hex);
        user.setEmail(user.getPhone());
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        int rows = userMapper.insert(user);
        if (rows > 0) {
            System.out.println("用户注册成功");
            return user.getUsername();
        }
        return null;
    }

    // 根据用户名和密码核验用户
    @Override
    public String  findUserByUP(String username, String password) {
        // 判断用户名和密码是否为null
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        String md5Hex = DigestUtils.md5Hex(password);
        User user = userMapper.findUserByUP(username, md5Hex);
        try{
            if (user != null) {
                String ticket = "JT_TICKET_"+System.currentTimeMillis()+username;
                ticket = DigestUtils.md5Hex(ticket);
                String json = objectMapper.writeValueAsString(user);
                // 将数据保存到redis中
                jedisCluster.set(ticket, json,"nx","ex",60*60*24);
                System.out.println("用户登陆成功");
                return ticket;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
