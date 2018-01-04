package org.ps.service;

import org.ps.domain.Task;
import org.ps.handler.ActuatorHandler;
import org.ps.uitl.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskServiceImpl implements TaskService{
    @Autowired
    ActuatorHandler ah;

    @Override
    public boolean addTask(Task task) throws Exception{
        task.setCreateTime(System.currentTimeMillis());
        task.setId(StringUtils.createUUID());
        return ah.addTask(task);
    }

    @Override
    public boolean updateTask(String taskId, boolean paused) throws Exception {
        return ah.updateTask(taskId, paused);
    }

}
