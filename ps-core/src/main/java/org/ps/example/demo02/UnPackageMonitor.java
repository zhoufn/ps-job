package org.ps.example.demo02;

import org.ps.example.demo02.domain.CompressShardTaskRepository;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
@IMonitor(name = "unPackageMonitor")
public class UnPackageMonitor extends MonitorHandler{

    @Autowired
    private CompressShardTaskRepository repository;

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
