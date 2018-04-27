package com.jt.web.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClientService;

    private ObjectMapper om = new ObjectMapper();

    // 获取Item数据
    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemById/" + itemId;
        try {
            String jsonResult = httpClientService.doGet(url);
            /*有可能返回的是null字符串*/
            if (StringUtils.isEmpty(jsonResult) || "null".equals(jsonResult))
                return null;
            return om.readValue(jsonResult, Item.class);
        } catch (URISyntaxException e) {
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

    @Override
    public ItemDesc findItemDescId(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
        try {
            String jsonResult = httpClientService.doGet(url);
            if (StringUtils.isEmpty(jsonResult) || "null".equals(jsonResult))
                return null;
            return om.readValue(jsonResult, ItemDesc.class);
        } catch (URISyntaxException e) {
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
}
