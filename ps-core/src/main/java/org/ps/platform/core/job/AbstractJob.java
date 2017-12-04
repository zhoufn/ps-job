package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJob implements SimpleJob{

    @Autowired
    protected ZookeeperHandler handler;

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        Task task = handler.getRunningTask();
        this.execute(shardingContext,task);
    }

    /**
     * @param shardingContext 分片上下文
     * @param runnigTask 当前执行中的任务
     */
    public abstract void execute(ShardingContext shardingContext, Task runnigTask);
}
