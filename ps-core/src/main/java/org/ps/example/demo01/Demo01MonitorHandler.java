package org.ps.example.demo01;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.ProcessException;
import org.ps.platform.core.exception.ReportException;
import org.ps.platform.core.handler.MonitorHandler;
import org.springframework.stereotype.Component;

@IMonitor(name = "demoMonitor")
@Component
public class Demo01MonitorHandler extends MonitorHandler {

    /**
     * Monitor周期性调用
     *
     * @param runningTask
     * @return Task是否已完成，true：完成，false：未完成。
     */
    @Override
    public boolean isDown(Task runningTask) throws MonitorException {
        System.out.println("*********** isDown of monitor for : " + runningTask.getId() + " *************");
        return false;
    }

    /**
     * Monitor周期调用生成进度
     *
     * @param runningTask
     * @return
     */
    @Override
    public int process(Task runningTask) throws ProcessException {
        System.out.println("*********** process of monitor for : " + runningTask.getId() + " *************");
        return 0;
    }

    /**
     * 生成任务报告
     *
     * @param runningTask
     */
    @Override
    public void createReport(Task runningTask) throws ReportException {
        System.out.println("*********** createReport of monitor for : " + runningTask.getId() + " *************");
    }
}
