package com.wh.searchall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.searchall.pojo.Blog;
import com.wh.searchall.pojo.Type;
import com.wh.searchall.service.BlogService;
import com.wh.searchall.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/27 19:43
 **/
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model){
        PageHelper.startPage(pagenum, 100);  //开启分页
        List<Type> types = typeService.getBlogType();
        //-1从导航点过来的
        if (id == -1){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTypeId(id);
        System.out.println("====================");

        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        System.out.println(pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
