package org.ps.example.demo03;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.springframework.stereotype.Component;

@Component
@IMonitor(name = "analysisMonitor")
public class AnalysisMonitor extends MonitorHandler{
    /**
     * 获取ShardTask总数
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getTotalShardTaskCount(Task runningTask) throws MonitorException {
        return 0;
    }

    /**
     * 获取完成的ShardTask的数量
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getDownShardTaskCount(Task runningTask) throws MonitorException {
        return 0;
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
