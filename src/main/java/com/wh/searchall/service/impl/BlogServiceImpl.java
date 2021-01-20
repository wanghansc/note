package com.wh.searchall.service.impl;

import com.wh.searchall.dao.BlogDao;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.BlogAndTag;
import com.wh.searchall.pojo.Tag;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.utils.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getById(id);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        int i = blogDao.saveBlog(blog);
        Long id = blog.getId();
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            i += blogDao.saveBlogAndTag(blogAndTag);

        }

        return i;
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        return blogDao.updateBlog(blog);
    }
}
