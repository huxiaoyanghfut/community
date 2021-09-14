package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:xiaoyang
 * @Title: DiscussPostService
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 19:45
 */
@Service
public class DiscussPostService {
    private DiscussPostMapper discussPostMapper;
    @Autowired
    public void setDiscussPostMapper(DiscussPostMapper discussPostMapper) {
        this.discussPostMapper = discussPostMapper;
    }

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
