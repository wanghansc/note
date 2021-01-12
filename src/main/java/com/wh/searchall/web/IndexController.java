package com.wh.searchall.web;

import com.wh.searchall.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/11 10:23
 **/
@Controller
public class IndexController {



    /**
      @description //TODO
     * @param
     * @return java.lang.String
     * @date 2021/1/11 10:42
     * //@PathVariable 记得路径绑定参数！
     */
    @GetMapping("/")
    public String index() {
        System.out.println("------index------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        System.out.println("------index------");
        return "blog";
    }
}
