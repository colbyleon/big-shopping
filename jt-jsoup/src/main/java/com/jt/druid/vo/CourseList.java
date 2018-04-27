package com.jt.druid.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.druid.pojo.Course;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseList {
    private List<Course> list;

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }
}
