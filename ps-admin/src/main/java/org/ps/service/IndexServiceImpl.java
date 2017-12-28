package org.ps.service;

import org.ps.domain.ServerStatus;
import org.ps.domain.Task;
import org.ps.handler.ActuatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    ActuatorHandler ah;
    @Override
    public ServerStatus showServerStatus() throws Exception {
        return ah.getServerStatus();
    }

    @Override
    public List<Task> showWaitingTaskList() throws Exception {
        return ah.getWaitingTaskList();
    }
}
