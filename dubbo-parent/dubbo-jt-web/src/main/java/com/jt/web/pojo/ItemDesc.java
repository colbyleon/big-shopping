package com.jt.web.pojo;

import com.jt.common.po.BasePojo;

import javax.persistence.Id;
import javax.persistence.Table;

public class ItemDesc extends BasePojo {
    private Long itemId;                //商品ID 一对一
    private String itemDesc;            //商品的描述

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
