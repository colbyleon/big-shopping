package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    //商品的新增页面

    /**
     * get：请求
     * localhost:8091/addUser?id=1$name=tom$age=19
     * RESTFul
     * localhost:8091/addUser/1/tom/19
     * @param moduleName
     * @return
     */
    @RequestMapping("/page/{moduleName}")
    public String addItem(@PathVariable String moduleName){
        return moduleName;
    }
}
