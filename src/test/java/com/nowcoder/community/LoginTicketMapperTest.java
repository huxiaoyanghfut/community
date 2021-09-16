package com.nowcoder.community;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author:xiaoyang
 * @Title: LoginTicketMapperTest
 * @ProjectName: community
 * @Description: 测试loginTicket数据访问层
 * @date: 2021/09/16 15:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTicketMapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void insertTest(){
        LoginTicket  ticket1 = new LoginTicket();
        ticket1.setUserId(163);
        ticket1.setStatus(0);
        ticket1.setTicket("abc");
        ticket1.setExpired(new Date(System.currentTimeMillis() + 3600*1000));
        int row = loginTicketMapper.insertLoginTicket(ticket1);
        System.out.println(row);
    }
    @Test
    public void selectTest(){
        LoginTicket  ticket2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(ticket2);
        loginTicketMapper.updateStatus("abc",1);
        System.out.println(loginTicketMapper.selectByTicket("abc").getStatus());
    }
}
