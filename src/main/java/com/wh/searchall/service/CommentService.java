package com.wh.searchall.service;

import com.wh.searchall.pojo.Comment;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/26 9:08
 **/
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);
}
