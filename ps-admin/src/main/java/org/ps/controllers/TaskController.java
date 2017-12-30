package org.ps.controllers;

import org.ps.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ps.domain.Task;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task/add")
    public boolean addTask(Task task) throws Exception{

        return taskService.addTask(task);
    }
}
