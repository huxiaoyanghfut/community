package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:xiaoyang
 * @Title: CommentMapper
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/21 16:21
 */
@Mapper
public interface CommentMapper {
    //根据实体(帖子)，实体类型：评论和回复，来查询一页评论数据
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);
    //统计所有评论数据
    int selectCountByEntity(int entityType, int entityId);
    //添加评论
    int insertComment(Comment comment);
}
