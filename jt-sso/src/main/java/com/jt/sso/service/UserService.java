package com.jt.sso.service;

import com.jt.sso.pojo.User;

import java.util.Map;

public interface UserService {
    Boolean findCheckUser(String param, String column);

    String saveUser(User user);

    String findUserByUP(String username, String password);

}
