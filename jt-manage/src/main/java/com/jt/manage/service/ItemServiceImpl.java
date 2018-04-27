package com.jt.manage.service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    public static final Logger log = Logger.getLogger(ItemServiceImpl.class.getName());

    @Override
    public EasyUIResult findItemAll(int page, int rows) {
        // 1. 获取商品的记录总数
        int total = itemMapper.findItemCount();
        // 2. 查询公布后的item数据
        /**
         * SELECT * FROM tb_item LIMIT 20,20
         */
        int start = (page - 1) * rows; // 确定起始的行数
        List<Item> itemList = itemMapper.findItemByPage(start, rows);
        return new EasyUIResult(total, itemList);
    }

    @Override
    public void saveItem(Item item, String desc) {
        // 将数据补全
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        log.info("~~~~~~~~{商品}");
        // 商品入库
        itemMapper.insert(item);
        // 商品描述入库
        ItemDesc itemDesc = new ItemDesc();
        // 通用Mapper入库操作后会回写当前数据的ID值
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(itemDesc.getCreated());

        itemDescMapper.insert(itemDesc);
    }

    @Override
    public String findItemCatName(int id) {
        return itemMapper.findItemCatById(id);
    }

    // 查询记录总数
    public int testFindCount() {
        return itemMapper.testFindCount();
    }

    @Override
    public void updateItem(Item item) {
        // 设定修改的时间
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);

    }

    @Override
    public void deleteItem(Long[] ids) {
//        itemMapper.deleteByIDS(ids);
        // 利用Mybatis动态删除
        itemMapper.deleteItemByIDs(ids);
    }

    @Override
    public void updateStatus(int status, Long[] ids) {
        // 通过mybatis实现批量的修改
        itemMapper.updateStatus(status, ids);

    }

    @Override
    public void updateItemDesc(Long itemId, String desc) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        List<ItemDesc> itemDescs = itemDescMapper.select(itemDesc);
        if (itemDescs.size()>0)
            return itemDescs.get(0);
        return null;
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }
}
