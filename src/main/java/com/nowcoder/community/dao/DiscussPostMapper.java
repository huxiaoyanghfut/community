package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:xiaoyang
 * @Title: DiscussPostMapper
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 19:01
 */
@Mapper
public interface DiscussPostMapper {
    //获取某一用户的所有帖子
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);
    //发布帖子
    int insertDiscussPost(DiscussPost discussPost);
    DiscussPost selectDiscussPostById(int id);

}
