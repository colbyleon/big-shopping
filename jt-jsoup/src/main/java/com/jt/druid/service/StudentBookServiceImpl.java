package com.jt.druid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.druid.mapper.*;
import com.jt.druid.pojo.*;
import com.jt.druid.vo.CourseList;
import com.jt.druid.vo.DirectionList;
import com.jt.druid.vo.StudentBookList;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentBookServiceImpl implements StudentBookService,BeanFactoryAware {
    @Autowired
    private StudentBookMapper bookMapper;

    @Autowired
    private StudentSectionMapper sectionMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DirectionMapper directionMapper;

    @Autowired
    private UnitMapper unitMapper;

    private BeanFactory beanFactory;

    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 1. 通过jsoup实现数据的查询
     * 2. 需要将返回json串转换成对象
     */
    @Override
    public void saveBook(String url, Integer status) {
        try {
            Response response = Jsoup.connect(url).ignoreContentType(true).execute();
            String jsonResult = response.body();
            StudentBookList bookList = objectMapper.readValue(jsonResult, StudentBookList.class);
            List<StudentBook> list = bookList.getList();
            for (StudentBook studentBook : list) {
                bookMapper.insert(studentBook);
                List<StudentSection> sections = studentBook.getSections();
                for (StudentSection section : sections) {
                    sectionMapper.insert(section);
                }
            }
            System.out.println("爬虫成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveCourse(String url, Integer directionId,Map<String,String> params) {
        try {
            Response response = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .data(params)
                    .execute();
            String jsouResult = response.body();
            CourseList courseList = objectMapper.readValue(jsouResult, CourseList.class);
            for (Course course : courseList.getList()) {
                course.setDirectionId(directionId);
                courseMapper.insert(course);
                for (Unit unit : course.getUnits()) {
                    unit.setCourseId(course.getCourseId());
                    unitMapper.insert(unit);
                    for (StudentBook studentBook : unit.getBooks()) {
                        studentBook.setUnitId(unit.getUnitId());
                        bookMapper.insert(studentBook);
                        List<StudentSection> sections = studentBook.getSections();
                        if (sections == null)continue;
                        for (StudentSection section : sections) {
                            section.setBookId(studentBook.getBookId());
                            sectionMapper.insert(section);
                        }
                    }
                }
            }
            System.out.println("爬取课程成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveDirection(String url) {
        // 为了让事务传递生效,不能直接在调用同类中的方法，需要通过代理调用
        StudentBookService bookService = beanFactory.getBean(StudentBookService.class);
        try {
            Response response = Jsoup.connect(url).method(Connection.Method.POST)
                    .ignoreContentType(true).execute();
            String directionJSON = response.body();
            List<Direction> directionList = objectMapper.readValue(directionJSON, DirectionList.class).getList();
            for (Direction direction : directionList) {
                System.out.println(direction.getName());
                directionMapper.insert(direction);
                String courseName = direction.getName();
                Map<String, String> params = new HashMap<>();
                params.put("dirName", courseName);
                String courseUrl = "http://www.it211.com.cn/book_test/getCourseByDirName";
                bookService.saveCourse(courseUrl,direction.getDirectionId(),params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
