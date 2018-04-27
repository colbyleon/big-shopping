package com.jt.common.service;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailService {
    private static final String HOST = "smtp.sina.com";
    private static final Integer PORT = 25; //465
    private static final String USERNAME = "gamenk@sina.com";
    private static final String PASSWORD = "31536827lxb";//hoie888xqshcbfgd
    private static final String EMAILFORM = "gamenk@sina.com";
//    private static final String HOST = "smtp.qq.com";
//    private static final Integer PORT = 587; //465
//    private static final String USERNAME = "592701174@qq.com";
//    private static final String PASSWORD = "okssjoxyzzjtbfgd";//okssjoxyzzjtbfgd
//    private static final String EMAILFORM = "592701174@qq.com";
    private static JavaMailSenderImpl mailSender = createMailSender();




    private static JavaMailSenderImpl createMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("utf-8");

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.timout", "25000");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.debug","true");

//        // QQ邮箱
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        sf.setTrustAllHosts(true);
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.ssl.socketFactory", sf);

        sender.setJavaMailProperties(prop);

        return sender;
    }

    /**
     * @param to        接收人
     * @param subject   主题
     * @param html      内容
     */
    public static boolean sendHtmlMail(String to, String subject, String html){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            messageHelper.setFrom(EMAILFORM,"系统名称");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html,true);
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
