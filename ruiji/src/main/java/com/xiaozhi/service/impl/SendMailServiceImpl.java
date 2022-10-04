package com.xiaozhi.service.impl;

import com.xiaozhi.common.CustomException;
import com.xiaozhi.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 20232
 */
@Service
public class SendMailServiceImpl implements SendMailService {


    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * 发送人的邮箱
     */
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override

    public void send(String email, String mailContext) {

//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        //消息助手
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);

        try {
            // 邮件的主题
            mimeMessageHelper.setSubject("后台管理系统注册验证码");
            // 邮件的发送人
            mimeMessageHelper.setFrom(fromEmail);
            // 邮件的接收人
            mimeMessageHelper.setTo(email);
            // 邮箱的内容,html:true为自动转换
            mimeMessageHelper.setText(mailContext, true);

        } catch (MessagingException e) {
            throw new CustomException("发送异常");
        }

        // 发送邮件
        javaMailSender.send(mimeMailMessage);
    }
}
