package com.jt.manage.controller;

import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    // 查询商品分类目录

    /**
     * 1. 如果没有扩展子节点信息应该有默认值0
     * 2. 如果扩展子节点信息，则传递id充当parentId
     *
     * @RequestParam作用
     * value = id 表示接收参数的类型
     * defaultValue 如果参数为null，测默认为0
     * required 是否必须传值
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITree> findItemCatByParentId(
            @RequestParam(value = "id",defaultValue = "0" ) Long parentId){
        List<EasyUITree> easyUIList = itemCatService.findEasyUIList(parentId);

        return easyUIList;
    }
}
