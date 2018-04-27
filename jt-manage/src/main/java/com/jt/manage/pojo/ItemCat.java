package com.jt.manage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;

import javax.persistence.*;

@Table(name = "tb_item_cat")
@JsonIgnoreProperties(ignoreUnknown = true) //表示忽略未知的属性，这个注解在这里不起作用，因为原来的getText方法被删除了
public class ItemCat extends BasePojo {

    /**
     * 一定要使用包装类型
     * 因为传对实体对象不为null的属性都将充当where条件
     * 不为包装类型时，默认值会是0,false
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;          //父级分类id
    private String name;            //分类名称
    private Integer status;         //状态码 默认值为1，可选值：1正常，2删除
    private Integer sortOrder;      //排序号
    private Boolean isParent;       //是否为父级




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
}
