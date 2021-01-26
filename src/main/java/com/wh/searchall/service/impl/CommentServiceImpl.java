package com.wh.searchall.service.impl;

import com.wh.searchall.dao.CommentDao;
import com.wh.searchall.pojo.Comment;
import com.wh.searchall.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/26 9:09
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> fistComments = commentDao.findByBlogIdAndParentCommentNull(blogId, Long.parseLong("-1"));
        for (Comment comment:fistComments) {
            Long id = comment.getId();
            String parentNickname1 = comment.getNickname();
            //子回复
            List<Comment> childComments = commentDao.findByBlogIdAndParentCommentNull(blogId,id);

            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            //清空缓存区
            tempReplys = new ArrayList<>();
        }
        return fistComments;
    }


    private void combineChildren(Long blogId, List<Comment> childComments, String parentNickname1) {
        //判断是否有1级评论
        if (childComments.size() > 0) {
            //找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
//                    查询出子二级评论,递归
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(Long blogId, Long childId, String parentNickname1) {
//        根据子一级评论的id找到子二级评论，递归查询
        List<Comment> replayComments = commentDao.findByBlogIdAndParentCommentNull(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }



    @Transactional
    @Override
    public int saveComment(Comment comment) {
        //获得父id
        Long parentCommentId = comment.getParentCommentId();
        //没有父级评论默认是-1
        if (parentCommentId != -1) {
            //有父级评论
            comment.setParentComment(commentDao.findByParentCommentId(comment.getParentCommentId()));
        } else {
            //没有父级评论
            comment.setParentCommentId((long) -1);
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }
}
