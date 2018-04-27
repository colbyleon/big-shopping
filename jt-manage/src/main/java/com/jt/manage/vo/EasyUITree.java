package com.jt.manage.vo;

import java.io.Serializable;

/**
 * 为了满足EasyUI的树形结构要求,避免数据冗余
 * id:      id编号
 * text:    节点名称
 * state：   开/关闭节点
 * 添加对应的get方法，通过@ResponseBody注解自动调用get方法，实现数据的获取
 */
public class EasyUITree implements Serializable {
    private Long id;
    private String text;            //分类名称
    private String state;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
