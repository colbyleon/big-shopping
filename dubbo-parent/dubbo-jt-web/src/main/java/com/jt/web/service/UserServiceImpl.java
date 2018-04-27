package com.jt.web.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpClientService httpClientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String saveUser(User user) {
        // 1. 定义uri
        String uri = "http://sso.jt.com/user/register";
        // 2. 设置参数
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("phone", user.getPhone());
        params.put("email", user.getEmail());
        // 3. 跨域访问，接收数据
        try {
            String jsonDate = httpClientService.doPost(uri, params, "utf-8");
            SysResult result = objectMapper.readValue(jsonDate, SysResult.class);
            return result.getStatus() == 200 ? (String) result.getData() : null;
        } catch (UnsupportedEncodingException e) {
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
     * 用户登陆操作
     * http://sso.jt.com/user/login
     * 封装参数 用户名u  密码p
     * 发起httpClient请求
     * 解析返回的数据进行校验
     */
    @Override
    public String findUserByUP(User user) {
        // 1. 定义uri
        String uri = "http://sso.jt.com/user/login";
        // 2. 封装参数
        Map<String, String> params = new HashMap<>();
        params.put("u", user.getUsername());
        params.put("p", user.getPassword());

        try {
            String jsonResult = httpClientService.doPost(uri, params, "utf-8");
            SysResult sysResult = objectMapper.readValue(jsonResult, SysResult.class);
            String ticket = (String) sysResult.getData();
            if (!StringUtils.isEmpty(ticket)) {
                return ticket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
