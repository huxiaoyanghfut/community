package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author:xiaoyang
 * @Title: MailClient
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/15 9:11
 */
@Component
public class MailClient {
    //启用日志
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    //声明一个邮件发送对象
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;//邮件发送端

    //邮件发送过程
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }
    }

}
