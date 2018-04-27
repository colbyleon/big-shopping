package com.jt.druid.service;

import com.jt.druid.mapper.UserMapper;
import com.jt.druid.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> userList = userMapper.select(null);
        return userList;
    }
}
