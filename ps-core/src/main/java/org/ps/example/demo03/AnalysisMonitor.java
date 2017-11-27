package org.ps.example.demo03;

import org.ps.example.demo03.domain.AnalysisShardTaskRepository;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@IMonitor(name = "analysisMonitor")
public class AnalysisMonitor extends MonitorHandler{

    @Autowired
    private AnalysisShardTaskRepository repository;

    /**
     * 获取JPA接口
     *
     * @return
     */
    @Override
    protected ShardTaskRepository getShardTaskRepository() {
        return this.repository;
    }

    /**
     * 生成任务报告
     *
     * @param runningTask
     */
    @Override
    public void createReport(Task runningTask) throws ReportException {

    }
}
