package com.jt.jsoup.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.druid.service.StudentBookService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

import static org.jsoup.Connection.*;

public class JsoupTest {
    // http://www.it211.com.cn/web/index_new.html?tedu
    /**
     *  1. 定位网站
     *  2. 定位目标元素的位置
     *  3. 获取数据
     *  4. 封装数据的结果
     */

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findTest() throws IOException {
        String url = "http://www.it211.com.cn/web/index_new.html?tedu";
        Document document = Jsoup.connect(url).get();
        String text = document.select(".b_search h2").get(0).text();
        System.out.println(text);
    }

    /* 只能爬取静态数据
    @Test
    public void findNum() throws IOException {
        String url = "http://www.it211.com.cn/web/index_new.html?tedu";
        Document document = Jsoup.connect(url).get();
        String num = document.select(".b_search #user_num").text();
        System.out.println(num);
    }*/

    @Test
    public void findNum() throws IOException {
        String url = "http://www.it211.com.cn/commonData/getCommonNum";
        // 告诉浏览器忽略我没加请求头
        Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        String jsonResult = response.body();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        JsonNode obj = jsonNode.get("obj");
        String string = obj.get("userNum").toString();
        System.out.println(string);
    }


    @Test
    public void getRecommendBook() throws IOException {
        String url = "http://www.it211.com.cn/book_test/getHotBook";
        Jsoup.connect(url).ignoreContentType(true).execute();
    }

    private StudentBookService bookService = null;



}
