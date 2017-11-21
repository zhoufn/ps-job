package org.ps.platform.core.handler;

import org.ps.platform.core.Task;
import org.ps.platform.core.TaskReport;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.PSException;
import org.ps.platform.core.exception.ProcessException;
import org.ps.platform.core.exception.ReportException;

/**
 * 监控策略类，Monitor会不间断的调用实现类的监控方法。
 */
public abstract class MonitorHandler {

    /**
     * 周期监控
     * @param runningTask
     * return Task是否已完成，true：完成，false：未完成。
     */
    public boolean monitor(Task runningTask) throws PSException{
        //更新进度
        runningTask.setProcess(this.process(runningTask));
        boolean flag = this.isDown(runningTask);
        if(flag){
            //生成报告
            this.createReport(runningTask);
        }
        return flag;
    }


    /**
     * Monitor周期性调用
     * @return Task是否已完成，true：完成，false：未完成。
     */
    public abstract boolean isDown(Task runningTask) throws MonitorException;

    /**
     * Monitor周期调用生成进度
     * @param runningTask
     * @return
     */
    public abstract int process(Task runningTask) throws ProcessException;

    /**
     * 生成任务报告
     * @param runningTask
     */
    public abstract void createReport(Task runningTask) throws ReportException;

}
