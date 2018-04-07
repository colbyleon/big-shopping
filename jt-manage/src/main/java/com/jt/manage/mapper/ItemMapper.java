package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends SysMapper<Item> {
    // 查询全部的商品信息
    List<Item> findItemAll();

    // 查询记录总数
    int findItemCount();

    /**
     * 根据分布查询商品信息
     * 说明，在mybatis中接口文件中不允许多值传参
     * 需要将多值封装为单值
     * 1. 将数据封装为对象
     * 2. 将数据封装为map对象
     * 3. 将数据封装为array/list集合
     *
     * @Param("key")用法： 说明：使用@Parma其实就是将数据封装为Map<Key,Value>
     */
    List<Item> findItemByPage(@Param("start") int start, @Param("rows") int rows);

    // 查询商品分类名称
    String findItemCatById(Integer id);

    void deleteItemByIDs(@Param("ids") Long[] ids);

    void updateStatus(@Param("status") int status, @Param("ids") Long[] ids);
}
