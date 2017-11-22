package org.ps.example.demo01;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.springframework.stereotype.Component;

@IMonitor(name = "demoMonitor")
@Component
public class Demo01MonitorHandler extends MonitorHandler {

    /**
     * 获取ShardTask总数
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getTotalShardTaskCount(Task runningTask) throws MonitorException {
        System.out.println("**************getTotalShardTaskCount******************");
        return 100;
    }

    /**
     * 获取完成的ShardTask的数量
     *
     * @param runningTask
     * @throws MonitorException
     */
    @Override
    public int getDownShardTaskCount(Task runningTask) throws MonitorException {
        System.out.println("**************getDownShardTaskCount******************");
        return 10;
    }

    /**
     * 生成任务报告
     *
     * @param runningTask
     */
    @Override
    public void createReport(Task runningTask) throws ReportException {
        System.out.println("**************createReport******************");
    }
}
