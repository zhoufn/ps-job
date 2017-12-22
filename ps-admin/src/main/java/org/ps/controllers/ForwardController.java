package org.ps.controllers;

import org.ps.domain.ServerStatus;
import org.ps.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 常规页面跳转Controller。
 * 跳转方法统一使用to开头。
 */
@Controller
public class ForwardController {

    @Autowired
    IndexService indexService;

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/index")
    public String toIndex(Model model) throws Exception {
        ServerStatus serverStatus = indexService.showServerStatus();
        model.addAttribute("serverStatus", serverStatus);
        return "admin/index";
    }

    @RequestMapping("/task")
    public String toTask(){return "admin/task";}

}
