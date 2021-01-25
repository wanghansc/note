package com.wh.searchall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.searchall.NotFoundException;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.Tag;
import com.wh.searchall.pojo.Type;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.service.TagService;
import com.wh.searchall.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/11 10:23
 **/
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    /**
      @description //TODO
     * @param
     * @return java.lang.String
     * @date 2021/1/11 10:42
     * //@PathVariable 记得路径绑定参数！
     */
    @GetMapping("/")
    public String index(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = blogService.getIndexBlog();
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)
        List<Tag> allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客

        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }

//    @PostMapping("/search")
//    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
//                         @RequestParam String query, Model model){
//
//        PageHelper.startPage(pagenum, 5);
//        List<Blog> searchBlog = blogService.getSearchBlog(query);
//        PageInfo pageInfo = new PageInfo(searchBlog);
//        model.addAttribute("pageInfo", pageInfo);
//        model.addAttribute("query", query);
//        return "search";
//    }

    @GetMapping("/blog/{id}")
    public String toLogin(@PathVariable Long id, Model model){
        Blog blog = blogService.getDetailedBlog(id);
//        System.out.println(blog.getContent());
        model.addAttribute("blog", blog);
        return "blog";
    }
}
