package com.jt.manage.factory;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    private  ClassPathXmlApplicationContext ctx;
    @Before
    public void init(){
        ctx = new ClassPathXmlApplicationContext(
                "classpath:applicationContext.xml");
    }

    @org.junit.Test
    public void test01(){
        User user = ctx.getBean("user",User.class);
        user.setAge(18);
        user.setName("dfdfd");
        System.out.println(user.getName());
    }
}
