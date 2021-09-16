package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author:xiaoyang
 * @Title: UserMapperTest
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 17:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelect(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("xiaoyang");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("huxiaoyang2019@163.com");
        user.setHeaderUrl("http://www.nowcoder.com/105.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);

        System.out.println(rows);
        User testUser = userMapper.selectByName("xiaoyang");
        System.out.println(testUser.getId());
    }

    @Test
    public void testUpdate() {
        int rows = userMapper.updateStatus(154, 1);
        System.out.println(rows);

        rows = userMapper.updateHeader(154, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(154, "hello");
        System.out.println(rows);
    }

    @Test
    public void testDiscussPost() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for (DiscussPost post : list){
            System.out.println(post);
        }
        System.out.println(discussPostMapper.selectDiscussPostRows(0));
    }

    @Test
    public void testInsertUserId() {
        User user = new User();
        user.setUsername("kobe1");
        user.setPassword("1234567");
        user.setSalt("abc");
        user.setEmail("basketball1@love.com");
        user.setHeaderUrl("http://www.nowcoder.com/108.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(user.getId());
        System.out.println(rows);
    }
}
