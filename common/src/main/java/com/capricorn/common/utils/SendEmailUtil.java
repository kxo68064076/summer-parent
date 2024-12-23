package com.capricorn.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
@Slf4j
public class SendEmailUtil {

    private static JavaMailSenderImpl staticjavaMailSender;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @PostConstruct
    public void init(){
        staticjavaMailSender = javaMailSender;
    }

    /**
     * 发送简单邮件
     * @param from，发送人，也就是我们自己的邮箱
     * @param address 收件地址，收件人邮箱
     * @param subject 邮件主题，等同于手写邮件时的主题，标题
     * @param text 邮件内容，全部邮件文本内容，simpleEmail只能是文字
     */
    public static void sendSimpleEmail(String from, String address, String subject, String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setFrom(from);
        mailMessage.setTo(address);
        staticjavaMailSender.send(mailMessage);
    }


    /**
     * html邮件
     *
     * @param from，发送人，也就是我们自己的邮箱
     * @param address 收件地址，收件人邮箱
     * @param subject 邮件主题，等同于手写邮件时的主题，标题
     */
    public static int sendHtmlMail(String from, String address, String subject, String name) {
        //获取MimeMessage对象
        MimeMessage message = staticjavaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        int flage=0;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            int randomCode = (int) ((Math.random() * 9 + 1) * 100000);
            messageHelper.setFrom(from);
            messageHelper.setTo(address);
            message.setSubject(subject);
            messageHelper.setText(buildContent(randomCode + "",name),true);
            staticjavaMailSender.send(message);
            log.info("发送成功");
        } catch (MessagingException e) {
            log.error("发送失败");
            flage = 1;
        }
        return flage;
    }

    public static String buildContent(String title,String name) {
        //加载邮件html模板
        Resource resource = new ClassPathResource("static/emailHtml/mailtemplate.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title,name);
    }


}