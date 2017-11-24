package org.ps.platform.core.handler;

import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.exception.SchedulerException;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 调度分片基类
 */
public abstract class SchedulerHandler extends Handler{

    @Autowired
    private ZookeeperHandler handler;

    /**
     * 调度方法
     */
    @Transactional
    public void shard(Task waitingTask) {
        try {
            List<ShardTask> shardTasks = this.scheduler(waitingTask);
            this.getShardTaskRepository().save(shardTasks);
        } catch (SchedulerException e) {
            e.printStackTrace();
            waitingTask.setEndTime(System.currentTimeMillis());
            waitingTask.setError(true);
            waitingTask.setErrorMsg(e.getMessage());
            handler.setWaitingTask2Down(waitingTask);
        }
    }

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    protected abstract List<ShardTask> scheduler(Task waitingTask) throws SchedulerException;

}
