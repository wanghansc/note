package com.wh.searchall.service.impl;

import com.wh.searchall.NotFoundException;
import com.wh.searchall.dao.BlogDao;
import com.wh.searchall.dao.TagDao;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.BlogAndTag;
import com.wh.searchall.pojo.Tag;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.service.TagService;
import com.wh.searchall.utils.BlogQuery;
import com.wh.searchall.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
//    @Autowired
//    private TagDao tagDao;
    @Autowired
    private TagService tagService;
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
        blog.setViews(10);
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
        int i = blogDao.updateBlog(blog);
        List<Tag> tags = blog.getTags();
        Long id = blog.getId();
        BlogAndTag blogAndTag = null;
        //先删除
        blogDao.deleteTag(blog);
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            i += blogDao.saveBlogAndTag(blogAndTag);

        }

        return i;
    }

    @Override
    public int deleteById(Blog blog) {
        int i =  blogDao.deleteBlog(blog.getId());
        i+=blogDao.deleteTag(blog);
        return i;
    }

    //首页

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Transactional
    @Override
    public Blog getDetailedBlog(Long id) {
        Blog detailedBlog = blogDao.getDetailedBlog(id);
        if (null == detailedBlog) {
            throw new NotFoundException("该博客不存在");
        } else {
            if (null != detailedBlog.getTagIds()) {
                List<Tag> tags = tagService.listTag(detailedBlog.getTagIds());
                detailedBlog.setTags(tags);
            }
            blogDao.updateViews(id);
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return detailedBlog;
    }
}
