package org.ps.platform.core.handler;

import org.ps.platform.core.Task;
import org.ps.platform.core.exception.MonitorException;
import org.ps.platform.core.exception.PSException;
import org.ps.platform.core.exception.ReportException;

/**
 * 监控策略类，Monitor会不间断的调用实现类的监控方法。
 */
public abstract class MonitorHandler {

    /**
     * 当前监控的Task
     */
    protected Task currentTask;

    /**
     * ShardTask总数
     */
    protected int totalShardTaskCount;

    /**
     * 已完成的ShardTask的数量
     */
    protected int downShardTaskCount;

    /**
     * 周期监控
     * @param runningTask
     * return Task是否已完成，true：完成，false：未完成。
     */
    public boolean monitor(Task runningTask) throws PSException{
        if(this.currentTask == null || !this.currentTask.getId().equals(runningTask.getId())){
            this.totalShardTaskCount = this.getTotalShardTaskCount(runningTask);
            this.currentTask = runningTask;
        }
        this.downShardTaskCount = this.getDownShardTaskCount(this.currentTask);
        runningTask.setProcess(this.downShardTaskCount / this.totalShardTaskCount);
        if(this.totalShardTaskCount == this.downShardTaskCount){
            this.createReport(this.currentTask);
            return true;
        }
        return false;
    }


    /**
     * 获取ShardTask总数
     * @throws MonitorException
     */
    public abstract int getTotalShardTaskCount(Task runningTask) throws MonitorException;

    /**
     * 获取完成的ShardTask的数量
     * @throws MonitorException
     */
    public abstract int getDownShardTaskCount(Task runningTask) throws MonitorException;


    /**
     * 生成任务报告
     * @param runningTask
     */
    public abstract void createReport(Task runningTask) throws ReportException;

}
