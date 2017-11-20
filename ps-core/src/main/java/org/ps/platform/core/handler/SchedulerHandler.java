package org.ps.platform.core.handler;

import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;

import java.util.List;

/**
 * 调度分片基类
 */
public abstract class SchedulerHandler {

    /**
     * 调度方法
     */
    public void shard(Task waitingTask){

    }

    /**
     * 用户自定义分片策略
     * @param waitingTask
     * @return
     */
    public abstract List<ShardTask> scheduler(Task waitingTask);

}
