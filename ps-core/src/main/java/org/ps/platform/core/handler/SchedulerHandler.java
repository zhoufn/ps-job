package org.ps.platform.core.handler;

import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;

import java.util.List;

/**
 * 调度分片基类
 */
public abstract class SchedulerHandler {

    public void scheduler(){

    }

    /**
     * 用户自定义分片策略
     * @param runningTask
     * @return
     */
    public abstract List<ShardTask> scheduler(Task runningTask);

}
