package com.wh.searchall.service;

import com.wh.searchall.pojo.Blog;
import com.wh.searchall.utils.BlogQuery;

import java.util.List;

/**
 * @author wanghan
 * @description Blog Service
 * @date 2021/1/13 10:05
 **/
public interface BlogService {
    List<Blog> getAllBlog();

    List<Blog> listBlog(BlogQuery blog);
}
