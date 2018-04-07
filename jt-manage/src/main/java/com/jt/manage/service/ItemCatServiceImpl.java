package com.jt.manage.service;

import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 采用JPA的思想后，传入的数据是对象
     * 如果是查询操作，那么对象传入的不为Null 的属性充当where条件
     */
    @Override
    public List<ItemCat> findItemCatList(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        itemCat.setStatus(1);
        return itemCatMapper.select(itemCat);
    }

    /**
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITree> findEasyUIList(Long parentId) {
        List<ItemCat> itemCatList = findItemCatList(parentId);
        List<EasyUITree> easyUITrees = new ArrayList<>();
        itemCatList.forEach(itemCat -> {
            EasyUITree easyUITree = new EasyUITree();
            easyUITree.setId(itemCat.getId());
            easyUITree.setText(itemCat.getName());
            easyUITree.setState(itemCat.getIsParent()? "closed":"open");
            easyUITrees.add(easyUITree);
        });
        return easyUITrees;
    }

}
