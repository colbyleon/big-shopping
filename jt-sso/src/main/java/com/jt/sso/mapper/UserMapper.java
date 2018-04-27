package com.jt.sso.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends SysMapper<User> {
    // 将数据进行封装
    int findCheckUser(@Param("param") String param,
                      @Param("column") String column);

    User findUserByUP(
            @Param("username") String username,
            @Param("password") String password);
}
