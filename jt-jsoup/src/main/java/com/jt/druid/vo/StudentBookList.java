package com.jt.druid.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.druid.pojo.StudentBook;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentBookList {

    private List<StudentBook> list = new ArrayList<>();

    public List<StudentBook> getList() {
        return list;
    }

    public void setList(List<StudentBook> list) {
        this.list = list;
    }
}
