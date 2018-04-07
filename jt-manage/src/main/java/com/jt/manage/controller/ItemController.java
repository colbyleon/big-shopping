package com.jt.manage.controller;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("/query")
    @ResponseBody   // 将java对象转化为json数据
    private EasyUIResult findItemAll(Integer page, Integer rows) {
        return itemService.findItemAll(page, rows);
    }

    //获取商品分类信息



    /**
     * url:"/item/cat/queryItemCatName",
     * data:{Id:val},
     */


    /*@RequestMapping("/cat/queryItemCatName")
    public void findItemCatName(Integer id, HttpServletResponse response){
        // 根据商品id获取分类的名称
        String name = itemService.findItemCatName(id);
        response.setContentType("text/html;charset=utf-8");
        try {
            // 通过该方法可以返回任意格式的数据
            response.getWriter().write(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 说明：@ResponseBody注解作用
     * 1. 将java对象转换为JSON字符串
     *  a.如果返回类型是Item对象/Object/List/array在转化时，默认采用的就是UTF-8编码
     *  b.如果返回值的类型是String则转换时默认采用的ISO-8859-1
     *  为什么??
     *  源码解析：
     *  public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
     *  总结：需要进行转化时切记使用对象（除String ）包装
     *  produces = "text/html;charset=utf-8"设置转化字符串格式
     * @param id
     * @return
     */
    @RequestMapping(value = "/cat/queryItemCatName",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String findItemCatName(Integer id) {
        return itemService.findItemCatName(id);
    }

    @RequestMapping("/testFindCount")
    @ResponseBody
    public String testFindCount(){
        return ""+itemService.testFindCount();
    }

    /**
     *
     * @param item  商品
     * @param desc  商品描述
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public SysResult saveItem(Item item, String desc){
        try {
            itemService.saveItem(item,desc);
            return  SysResult.oK(); // 表示新增成功
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "新增失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public SysResult updateItem(Item item ,String desc){
        try {
            // 更新商品信息
            itemService.updateItem(item);
            // 更新商品描述
            itemService.updateItemDesc(item.getId(),desc);

            return  SysResult.oK(); // 表示更新成功
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "更新失败");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public SysResult deleteItem(String ids) {
        try {
            String[] strs = ids.split(",");
            Long[] iDs = new Long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                iDs[i] = Long.parseLong(strs[i]);
            }
            itemService.deleteItem(iDs);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "删除失败");
        }

    }

    @RequestMapping("/instock")
    @ResponseBody
    public SysResult instockItem(Long[] ids){ // 传过来的字符串spring mvc 会自动将其分割成数组，不用像上面的方法一样自己分割
        try {
            int status = 2; // 下架参数
            itemService.updateStatus(status, ids);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"商品下架失败");
        }

    }

    @RequestMapping("/reshelf")
    @ResponseBody
    public SysResult reshelfItem(Long[] ids){
        try {
            int status = 1; // 上架参数
            itemService.updateStatus(status, ids);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201,"商品下架失败");
        }

    }

    //$.getJSON('/item/query/item/desc/'+data.id,function(_data){
    @RequestMapping(value = "/query/item/desc/{itemId}")
    @ResponseBody
    public SysResult findItemDescById(@PathVariable Long itemId){
        ItemDesc itemDesc = null;
        try {
            itemDesc = itemService.findItemDescById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "查询失败");
        }
        return new SysResult(itemDesc);
    }


}
