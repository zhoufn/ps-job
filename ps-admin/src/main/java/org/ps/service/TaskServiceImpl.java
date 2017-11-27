package org.ps.service;

import org.ps.domain.Task;
import org.ps.handler.ZookeeperHandler;
import org.ps.uitl.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TaskServiceImpl implements TaskService{

    @Autowired
    private ZookeeperHandler zookeeperHandler;

    @Override
    public void addTask(Task task) {
        zookeeperHandler.addWaitingTask(task);
    }
}
