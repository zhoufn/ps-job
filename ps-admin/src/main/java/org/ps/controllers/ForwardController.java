package org.ps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 常规页面跳转Controller。
 * 跳转方法统一使用to开头。
 */
@Controller
public class ForwardController {

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/index")
    public String index(){return "admin/index";}

    @RequestMapping("/tasks")
    public String tasks(){return "admin/tasks";}

}
