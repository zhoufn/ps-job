package org.ps.example.demo01;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@IMonitor(name = "demoMonitor")
public class DemoMonitor extends MonitorHandler {

    @Autowired
    private DemoShardTaskRepository repository;

    /**
     * 生成任务报告
     *
     * @param runningTask
     */
    @Override
    public void createReport(Task runningTask) throws ReportException {

    }

    /**
     * 获取JPA接口
     *
     * @return
     */
    @Override
    protected ShardTaskRepository getShardTaskRepository() {
       return this.repository;
    }
}
