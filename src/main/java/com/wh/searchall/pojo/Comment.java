package com.wh.searchall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/12 10:14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    private boolean adminComment;
    private Long parentCommentId;
    private String parentNickname;
    private Long blogId;

    private List<Comment> replyComments = new ArrayList<>();

    private Comment parentComment;

}
