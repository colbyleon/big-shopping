package com.jt.dubbo.service;

import com.jt.dubbo.pojo.Item;

import java.util.List;

public interface DubboSearchService {
    List<Item> findItemByKey(String keyWord);
}
