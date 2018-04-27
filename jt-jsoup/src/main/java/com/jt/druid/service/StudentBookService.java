package com.jt.druid.service;

import java.util.Map;

public interface StudentBookService {
    void saveBook(String url,Integer status);
    void saveCourse(String url, Integer dircetionId,Map<String,String> params);
    void saveDirection(String url);
}
