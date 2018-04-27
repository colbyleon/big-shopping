package com.jt.sso.controller;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    /* http://sso.jt.com/user/check
     * 相应的用户名、邮箱、手机是否已经被注册
     * */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public MappingJacksonValue findCheckUser(
            @PathVariable String param,
            @PathVariable Integer type, String callback) {
        String column = null;
        switch (type) {
            case 1:
                column = "username";
                break;
            case 2:
                column = "phone";
                break;
            case 3:
                column = "email";
                break;
        }
        Boolean isExist = userService.findCheckUser(param, column);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(SysResult.oK(isExist));
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping("/register")
    @ResponseBody
    public SysResult saveUser(User user) {
        String username = userService.saveUser(user);
        SysResult sysResult = username == null
                ? SysResult.build(201, "用户信息不完整")
                : SysResult.oK(username);

        return sysResult;
    }

    @RequestMapping("/login")
    @ResponseBody
    public SysResult findUserByUP(@RequestParam("u") String username,
                             @RequestParam("p") String password) {
        String ticket = null;
        try {
            ticket = userService.findUserByUP(username, password);
            if (StringUtils.isEmpty(ticket)) return SysResult.build(201,"未找到此用户");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.oK(ticket);
    }

    //http://sso.jt.com/user/query/1f0fa8f9ca8e5bbc38db23e7a77d8f52?callback=jsonp1523962604135&_=1523962604205
    /*  已经登陆用户的回显
    返回jsonp*/
    @RequestMapping("/query/{ticket}")
    @ResponseBody
    public MappingJacksonValue findUserByTicket(@PathVariable String ticket,String callback) {
        MappingJacksonValue jacksonValue = null;
        try {
            String userJSON = jedisCluster.get(ticket);
            if (!StringUtils.isEmpty(userJSON)) {
                jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
                jacksonValue.setJsonpFunction(callback);
                return jacksonValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jacksonValue = new MappingJacksonValue(SysResult.build(201, "查询失败"));
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }
}
