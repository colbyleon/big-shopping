package com.jt.manage.service;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> findItemCatList(Long parentId);

    List<EasyUITree> findEasyUIList(Long parentId);

    ItemCatResult findItemCatAll();
}
