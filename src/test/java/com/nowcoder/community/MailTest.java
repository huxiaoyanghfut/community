package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import com.sun.xml.internal.bind.CycleRecoverable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author:xiaoyang
 * @Title: MailTest
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/15 9:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    //发送文本
    public void testTextMail() {
        mailClient.sendMail("huxiaoyang2019@163.com","send text","发送邮件测试");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context() ;
        context.setVariable("username","晓阳");

        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);

        mailClient.sendMail("huxiaoyang2019@163.com", "HTML TEST", content);

    }
}
