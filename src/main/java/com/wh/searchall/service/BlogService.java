package com.wh.searchall.service;

import com.wh.searchall.pojo.Blog;
import com.wh.searchall.utils.BlogQuery;

import java.util.List;
import java.util.Map;

/**
 * @author wanghan
 * @description Blog Service
 * @date 2021/1/13 10:05
 **/
public interface BlogService {
    List<Blog> getAllBlog();

    List<Blog> listBlog(BlogQuery blog);

    Blog getBlog(Long id);

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteById(Blog blog);

    List<Blog> getIndexBlog();

    List<Blog> getAllRecommendBlog();

    Blog getDetailedBlog(Long id);

    List<Blog> getByTagId(Long tagId);

    List<Blog> getByTypeId(Long typeId);

    Map<String,List<Blog>> archiveBlog();

    int countBlog();
}
