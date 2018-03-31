package com.jt.manage.controller;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 查询全部的用户信息

    /**
     * 需求：需要将List集合信息返回给页面，面面中通过jstl标签直接获取参数
     * 思路：通过request域进行数据的传递,通过Model对象将数据保存到request域中
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "userList";
    }
}
