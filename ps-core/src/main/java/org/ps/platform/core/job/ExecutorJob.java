package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.stereotype.Component;

/**
 * PS平台下用来执行具体分片任务的执行器
 */
@Component
public class ExecutorJob extends AbstractJob{

    /**
     * @param shardingContext 分片上下文
     * @param runnigTask      当前执行中的任务
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask) {

    }
}
