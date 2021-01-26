package com.wh.searchall.dao;

import com.wh.searchall.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/26 9:10
 **/
@Mapper
public interface CommentDao {
    List<Comment> findByBlogIdAndParentCommentNull(@Param("blogId") Long blogId, @Param("parentCommentId") Long parentCommentId);

    Comment findByParentCommentId(@Param("parentCommentId")Long parentCommentId);

    int saveComment(Comment comment);
}
