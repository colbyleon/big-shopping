package com.jt.web.controller;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.DubboSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private DubboSearchService searchService;

    @RequestMapping("/search")
    public String queryKey(@RequestParam("q") String keyWord , Model model){
        // 0. 处理乱码问题
        try {
            keyWord = new String(keyWord.getBytes("iso8859-1"), "utf-8");
            System.out.println(keyWord);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Item> itemList = searchService.findItemByKey(keyWord);
        model.addAttribute("query", keyWord);
        model.addAttribute("itemList", itemList);
        return "search";
    }
}
