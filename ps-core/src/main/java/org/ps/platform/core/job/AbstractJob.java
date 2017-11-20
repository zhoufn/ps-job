package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;

public abstract class AbstractJob implements SimpleJob{

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        ZookeeperHandler handler = (ZookeeperHandler) PsContext.getBean(ZookeeperHandler.class);
        Task task = handler.getRunningTask();
        this.execute(handler,shardingContext,task);
    }

    /**
     * @param handler  zookeeper操作类
     * @param shardingContext 分片上下文
     * @param runnigTask 当前执行中的任务
     */
    public abstract void execute(ZookeeperHandler handler,ShardingContext shardingContext, Task runnigTask);
}
