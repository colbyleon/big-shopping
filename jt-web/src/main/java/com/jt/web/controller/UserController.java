package com.jt.web.controller;

import com.jt.common.service.MailService;
import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    // /user/register.html
    @RequestMapping("/{module}")
    public String index(@PathVariable String module) {
        return module;
    }

    //http://www.jt.com/service/user/doRegister
    // 用户注册
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user) {
        try {
            String username = userService.saveUser(user);
            return SysResult.oK(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "用户注册失败");
    }

    //http://www.jt.com/service/user/doLogin?r=0.23088177216590533
    //用户登陆
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            String ticket = userService.findUserByUP(user);
            // 判断ticket是否为null
            if (!StringUtils.isEmpty(ticket)){
                String cookieName = "JT_TICKET";
                System.out.println("----------"+request.getContextPath());

                CookieUtils.setCookie(request,response,cookieName,ticket);
                return SysResult.oK(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "登陆失败");
    }

    // /WEB-INF/views/logout.jsp

    /**
     * 1. 先获取cookie中的ticket
     * 2. 将redis中的数据删除
     * 3. 将cookie中的数据删除
     * 4. 页面重定向到系统首页
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        String cookie = CookieUtils.getCookieValue(request, "JT_TICKET");
        jedisCluster.del(cookie);
        CookieUtils.deleteCookie(request,response,"JT_TICKET");
        return "redirect:/index.html"; // 1. 重定向redirect 2. 转发forward或者不写
    }
}
