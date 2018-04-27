package com.jt.manage.controller.web;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web")
public class WebItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/findItemById/{itemId}")
    @ResponseBody
    public Item findItemById(@PathVariable Long itemId) {
        try {
            return itemService.findItemById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/item/findItemDescById/{itemId}")
    @ResponseBody
    public ItemDesc findItemDescById(@PathVariable Long itemId) {
        try {
            return itemService.findItemDescById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
