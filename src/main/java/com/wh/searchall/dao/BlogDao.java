package com.wh.searchall.dao;

import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.BlogAndTag;
import com.wh.searchall.utils.BlogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/19 16:04
 **/
@Mapper
public interface BlogDao {
    List<Blog> getAllBlog();

    List<Blog> listBlog(BlogQuery blog);

    Blog getById(Long id);

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);

//    int saveBlogAndTag(BlogAndTag blogAndTag);
//
//    int deleteBlog(Long id);

}
