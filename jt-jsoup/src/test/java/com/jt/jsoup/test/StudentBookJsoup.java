package com.jt.jsoup.test;

import com.jt.druid.service.StudentBookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;


public class StudentBookJsoup {


    private StudentBookService bookService;

    @Before
    public void init(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
        bookService = ctx.getBean(StudentBookService.class);
    }

    @Test
    public void findHotBook(){
        String url = "http://www.it211.com.cn/book_test/getHotBook";
        bookService.saveBook(url,1);
    }

    @Test
    public void findAllDirection(){
        String url = "http://www.it211.com.cn/book_test/getAllDirection";
        bookService.saveDirection(url);
    }
}
