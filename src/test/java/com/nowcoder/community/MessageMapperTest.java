package com.nowcoder.community;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author:xiaoyang
 * @Title: MessageMapperTest
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/10/03 14:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MessageMapperTest {
    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void testSelectLatestNotice(){
        Message message = messageMapper.selectLatestNotice(138, "comment");
        System.out.println(message);
    }

    @Test
    public void testSelectNoticeCount(){
        int messageCount = messageMapper.selectNoticeCount(138, "comment");
        System.out.println(messageCount);
    }

    @Test
    public void testSelectUnreadNoticeCount(){
        int messageCount = messageMapper.selectUnreadNoticeCount(146, "like");
        System.out.println(messageCount);
    }

}
