package com.jt.druid.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.druid.pojo.Direction;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionList {
    private List<Direction> list;

    public List<Direction> getList() {
        return list;
    }

    public void setList(List<Direction> list) {
        this.list = list;
    }
}
