package com.jt.manage.service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

public interface ItemService {
    EasyUIResult findItemAll(int page,int rows);

    String findItemCatName(int id);

    int testFindCount();

    void saveItem(Item item,String desc);

    void updateItem(Item item);

    void deleteItem(Long[] ids);

    void updateStatus(int status, Long[] ids);

    void updateItemDesc(Long itemId, String desc);

    ItemDesc findItemDescById(Long itemId);

    Item findItemById(Long itemId);
}
