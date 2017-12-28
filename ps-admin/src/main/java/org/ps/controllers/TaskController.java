package org.ps.controllers;

import org.ps.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    public String addTask(){
        return "";
    }
}
