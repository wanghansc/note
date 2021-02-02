package com.wh.searchall.service.impl;

import com.wh.searchall.NotFoundException;
import com.wh.searchall.dao.BlogDao;
import com.wh.searchall.dao.TagDao;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.BlogAndTag;
import com.wh.searchall.pojo.Tag;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.service.FileService;
import com.wh.searchall.service.TagService;
import com.wh.searchall.utils.BlogQuery;
import com.wh.searchall.utils.MarkdownUtils;
import com.wh.searchall.utils.SearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleFacetAndHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/19 16:33
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private SolrTemplate solrTemplate;
    @Value("${spring.data.solr.core1}")
    private String collection;
    @Value("${spring.data.solr.core2}")
    private String collection2;

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
        fileService.importAll();
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
//        fileService.deleteAll();
        fileService.importAll();
        return i;
    }

    @Override
    public int deleteById(Blog blog) {
        int i =  blogDao.deleteBlog(blog.getId());
        i+=blogDao.deleteTag(blog);
        fileService.deleteByBlogId(blog.getId());
        fileService.importAll();
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

    @Override
    public List<Blog> getByTagId(Long tagId) {
        List<Blog> byTagId = blogDao.getByTagId(tagId);
        for (Blog blog : byTagId) {
            List<Tag> tags = tagService.listTag(blog.getTagIds());
            blog.setTags(tags);
        }
        return byTagId;
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlog().size();
    }

    @Override
    public List<SearchItem> getSearchBlog(String q, int pageNum, int pageSize) {
        HighlightQuery query = new SimpleFacetAndHighlightQuery();
        Criteria criteria = new Criteria("blog_content");
        criteria.is(q);
        query.addCriteria(criteria);

        //设置高亮查询
        HighlightOptions highlightOptions = new HighlightOptions();
        //设置高亮显示区域
        highlightOptions.addField("blog_content");
        highlightOptions.setNrSnipplets(3);
        highlightOptions.setFragsize(40);
        highlightOptions.setSimplePrefix("<span style='color:red'>");
        highlightOptions.setSimplePostfix("</span>");

        //分页
        query.setOffset((pageNum + 0L)* pageSize);
        query.setHighlightOptions(highlightOptions);

        HighlightPage<SearchItem> highlightPage = this.solrTemplate.queryForHighlightPage(this.collection, query, SearchItem.class);

        List<HighlightEntry<SearchItem>> highlightEntries = highlightPage.getHighlighted();

        for (HighlightEntry<SearchItem> highlightEntry : highlightEntries) {
            //实体对象，原始的实体对象
            SearchItem entity = highlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            //如果有高亮，就取高亮
            if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets().size() > 0) {
                entity.setHighLight(highlights.get(0).getSnipplets());
            }
        }
        List<SearchItem> list = highlightPage.getContent();
        for (int i=0;i<list.size();i++) {
            List<String> highLight = list.get(i).getHighLight();
            if (highLight==null||highLight.size()==0) {
                SearchItem searchItem = list.get(i);
                List<String> h = new ArrayList<>();
                h.add("<span style='color:red'>" + q + "</span>");
                searchItem.setHighLight(h);
                break;
            }

//            for (int j = 0; j < highLight.size(); j++) {
//                highLight.set(j,MarkdownUtils.markdownToHtmlExtensions(highLight.get(j)));
//            }
        }

        HighlightPage<SearchItem> highlightPage2 = this.solrTemplate.queryForHighlightPage(this.collection2, query, SearchItem.class);

        List<HighlightEntry<SearchItem>> highlightEntries2 = highlightPage2.getHighlighted();

        for (HighlightEntry<SearchItem> highlightEntry1 : highlightEntries2) {
            //实体对象，原始的实体对象
            SearchItem entity = highlightEntry1.getEntity();
            List<HighlightEntry.Highlight> highlights1 = highlightEntry1.getHighlights();
            //如果有高亮，就取高亮
            if (highlights1 != null && highlights1.size() > 0 && highlights1.get(0).getSnipplets().size() > 0) {
                entity.setHighLight(highlights1.get(0).getSnipplets());
            }
        }
        List<SearchItem> list2 = highlightPage2.getContent();
        for (int i=0;i<list2.size();i++) {
            List<String> highLight = list2.get(i).getHighLight();
            if (highLight==null||highLight.size()==0) {
                SearchItem searchItem = list2.get(i);
                List<String> h = new ArrayList<>();
                h.add("<span style='color:red'>" + q + "</span>");
                searchItem.setHighLight(h);
                break;
            }

//            for (int j = 0; j < highLight.size(); j++) {
//                highLight.set(j,MarkdownUtils.markdownToHtmlExtensions(highLight.get(j)));
//            }
        }
        if (list2==null||list2.size()==0) {
            return list;
        }
        if (list == null||list.size()==0) {
            return list2;
        }
        List<SearchItem> list3 = new ArrayList<>();
        if (list != null && list2 != null && list2.size()>0 && list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                SearchItem searchItem = list.get(i);
                list3.add(searchItem);
            }
            for (int i = 0; i < list2.size(); i++) {
                SearchItem searchItem = list2.get(i);
                list3.add(searchItem);
            }
            return list3;
        }
        return list3;
    }
}
