package com.wh.searchall.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.service.TagService;
import com.wh.searchall.service.TypeService;
import com.wh.searchall.utils.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/13 11:59
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = blogService.getAllBlog();
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                         BlogQuery blog, Model model) {
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = blogService.listBlog(blog);
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        return "admin/blogs :: blogList";
    }


}
