package com.jt.manage.mapper;

import com.jt.manage.pojo.User;

import java.util.List;

public interface UserMapper {

    // 查询user表的全部信息
    List<User> findAll();
}
