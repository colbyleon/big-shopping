package com.jt.manage.controller.web;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


//Request URL: http://manage.jt.com/web/itemcat/all?callback=category.getDataService
@Controller
@RequestMapping("/web")
public class WebItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 前台以JSONP的形式进行调用，需要将数据按照约定进行封装
     * 返回格式callback( json )
     * @param callback
     */
    @RequestMapping("/itemcat/all")
    @ResponseBody //将返回对象转化为JSON数据
    public MappingJacksonValue findItemCatAll(String callback){
        // 获取三级商品分类的包装类型
        ItemCatResult itemCatResult = itemCatService.findItemCatAll();
        MappingJacksonValue jacksonValue = new MappingJacksonValue(itemCatResult);
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }


}
