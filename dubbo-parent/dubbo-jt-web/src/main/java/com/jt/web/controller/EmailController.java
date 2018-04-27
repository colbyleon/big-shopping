package com.jt.web.controller;

import com.jt.common.service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailController {

    /**
     * 邮箱验证
     * http://www.jt.com/validateuser/isEmailEngaged?email=6144545456@qq.com&r=0.11657756246114848
     */
    @RequestMapping("/isEmailEngaged")
    @ResponseBody
    public Map<String, Integer> validateMail(String email) {
        String subject = "请进行验证";
        String html = "<h1>请点击下面的链接验证邮箱<h1><br/>" +
                "<a href='www.jt.com/index.html?email=a12a1a12a12ab215c525ce2f'>进行验证</a>";

        boolean flag = MailService.sendHtmlMail(email, subject, html);
        Map<String, Integer> params = new HashMap<>();

        params.put("success", flag ? 0 : 2);
        return params;
    }


}
