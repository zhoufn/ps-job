package org.ps.controllers;

import org.ps.domain.ServerStatus;
import org.ps.enums.TaskType;
import org.ps.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ps.domain.Task;

import java.util.List;

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
        List taskList = indexService.showWaitingTaskList();
        model.addAttribute("serverStatus", serverStatus);
        model.addAttribute("taskList", taskList);
        return "admin/index";
    }

    @RequestMapping("/task")
    public String toTask(){return "admin/task";}

    @GetMapping("/task/add")
    public String toAddTask(Model model) throws Exception{
        List<Task> taskList = indexService.showWaitingTaskList();
        model.addAttribute("taskList", taskList);
        return "admin/add-task";
    }
    @GetMapping("/task/list/{taskType}")
    public String toTaskList(Model model, @PathVariable("taskType") TaskType taskType) throws Exception{
        List<Task> taskList = indexService.showTaskList(taskType);
        model.addAttribute("taskList", taskList);
        return "admin/task-list";
    }

}
