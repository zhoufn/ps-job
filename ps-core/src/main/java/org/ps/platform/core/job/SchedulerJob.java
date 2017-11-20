package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 平台下用来执行分片策略的调度器
 */
public class SchedulerJob extends AbstractJob{

    /**
     * @param handler         zookeeper操作类
     * @param shardingContext 分片上下文
     * @param runnigTask      当前执行中的任务
     */
    @Override
    public void execute(ZookeeperHandler handler, ShardingContext shardingContext, Task runnigTask) {
        runnigTask = handler.setWaitingTask2Running();
    }
}
