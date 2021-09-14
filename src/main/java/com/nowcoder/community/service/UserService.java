package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:xiaoyang
 * @Title: UserService
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 19:36
 */
@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserById(int id){
        return userMapper.selectById(id);
    }


}
