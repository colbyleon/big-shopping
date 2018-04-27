package com.jt.manage.service;

import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 采用JPA的思想后，传入的数据是对象
     * 如果是查询操作，那么对象传入的不为Null 的属性充当where条件
     * <p>
     * redis缓存实现的步骤
     * 1. 定义redis的key值
     * 2. 通过jedis查询缓存,判断返回的数据是否为空
     * 3. 如果数据不为空,将json转换成list集合对象
     * 4. 如果数据为空，查询数据库并保存进redis
     */

    /**
     * 基于Aop实现redis的缓存
     * 注意：
     * 因为这个方法是被findEasyUIList直接调用的，是直接调用的目标对象中的方法
     * 而不是代理对象的方法，所以aop对它根本不起作用
     */
    @Override
    public List<ItemCat> findItemCatList(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        itemCat.setStatus(1);
        List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
        return itemCatList;
    }

    /**
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
            easyUITree.setState(itemCat.getIsParent() ? "closed" : "open");
            easyUITrees.add(easyUITree);
        });
        return easyUITrees;
    }

    /**
     * 该方法实现三级商品分类目录
     * 1. 首先查询全部的一级目录 parentId=0
     * 2. 查询二级商品分类      parentId= 一级.id
     * 3. 查询三级商品分类      parentId= 二级.id
     * 根据循环遍历的方式频繁的操作数据库，性能太低
     * 解决：通过合理的数据结构，灵活的获取三级商品的数据结构
     * 使用map集合实现该操作
     *
     * @return
     */
    @Override
    public ItemCatResult findItemCatAll() {
        // 1. 查询所有目录信息
        ItemCat itemCatTemp = new ItemCat();
        itemCatTemp.setStatus(1); //只查询所有正常的属性
        List<ItemCat> itemCatAllList = itemCatMapper.select(itemCatTemp);
        // 2. 按parendId存入到map中
        Map<Long, List<ItemCat>> map = new HashMap<>();
        for (ItemCat itemCat : itemCatAllList) {
            Long parentId = itemCat.getParentId();
            if (map.containsKey(parentId)) {
                //证明已经存在parentId,只需要将itemCat对象添加到list集合即可
                map.get(parentId).add(itemCat);
            } else {
                // list集合还不存在
                ArrayList<ItemCat> itemCatList = new ArrayList<>();
                itemCatList.add(itemCat);
                map.put(parentId, itemCatList);
            }
        }
        // 3. 获取一级目录
        // 3.1 创建装一级目录数据的集合
        List<ItemCatData> itemCatDataList1 = new ArrayList<>();
        // 3.2 遍历一级目录
        for (ItemCat itemCat1 : map.get(0L)) {
            // 3.3.3 创建二级目录数据的集合
            ArrayList<ItemCatData> itemCatDataList2 = new ArrayList<>();
            // 3.3.4 遍历二级目录
            for (ItemCat twoCat2 : map.get(itemCat1.getId())) {
                // 创建三级目录数据集合
                List<String> itemCatDataList3 = new ArrayList<>();
                // 遍历三级目录并封装数据到集合
                for (ItemCat itemCat3 : map.get(twoCat2.getId())) {
                    itemCatDataList3.add("/products/" + itemCat3.getId() + "|" + itemCat3.getName());
                }
                // 创建二级目录数据对象
                ItemCatData itemCatData2 = new ItemCatData();
                // 将三级目录装入对象中，设置相关属性
                itemCatData2.setUrl("/products/" + twoCat2.getId());
                itemCatData2.setName(twoCat2.getName());
                itemCatData2.setItems(itemCatDataList3);
                // 将二级目录数据对象加入二级目录集合
                itemCatDataList2.add(itemCatData2);
            }
            // 3.3.5 已经将二级目录的数据用集合封装好，创建一级目录的数据对象
            ItemCatData itemCatData1 = new ItemCatData();
            // 3.3.6 将集合存入一级目录数据对象，设置相关属性
            itemCatData1.setUrl("/products/" + itemCat1.getId() + ".html");
            itemCatData1.setName("<a href='" + itemCatData1.getUrl() +
                    "'>" + itemCat1.getName() + "</a>");
            itemCatData1.setItems(itemCatDataList2);
            // 3.3.7 将一级数据对象加一级目录集合
            itemCatDataList1.add(itemCatData1);

            // 将数据的长度维护到14条
            if (itemCatDataList1.size() >= 14) break;
        }
        // 4. 封装结果
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setItemCats(itemCatDataList1);
        return itemCatResult;
    }










    /* @Autowired
    private RedisService redisService;

    private ObjectMapper om = new ObjectMapper();*/
    /* @Override
    public List<ItemCat> findItemCatList(Long parentId) {
        System.out.println("findItemCatList----->=" );
        //1. 定义key值
        String key = "ITEM_CAT_" + parentId;
        //2. 从缓存中获取数据
        String jsonData = redisService.get(key);
        //3. 判断缓存中是否有数据

        List<ItemCat> itemCatList = null;
        try {
            if (!StringUtils.isEmpty(jsonData)) {
                // 4. 直接从缓存中取出并返回
                itemCatList = Arrays.asList(om.readValue(jsonData, ItemCat[].class));
                System.out.println("--------------------------------------------------取自缓存-------------------------------------------------------");
                return itemCatList;
            }
            //5. 表示redis中没有缓存,先从数据库取数据
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            itemCat.setStatus(1);
            itemCatList = itemCatMapper.select(itemCat);
            //6. 将数据存入缓存中
            String itemCatJson = om.writeValueAsString(itemCatList);
            redisService.set(key, itemCatJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemCatList;
    }*/
}
