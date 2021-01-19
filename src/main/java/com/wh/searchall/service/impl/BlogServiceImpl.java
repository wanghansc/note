package com.wh.searchall.service.impl;

import com.wh.searchall.dao.BlogDao;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.utils.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/19 16:33
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public List<Blog> listBlog(BlogQuery blog) {
        return blogDao.listBlog(blog);
    }
}
