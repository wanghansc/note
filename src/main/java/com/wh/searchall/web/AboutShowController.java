package com.wh.searchall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/28 20:14
 **/
@Controller
public class AboutShowController {
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
