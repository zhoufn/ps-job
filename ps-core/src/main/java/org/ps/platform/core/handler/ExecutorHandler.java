package org.ps.platform.core.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.exception.ExecutorException;

/**
 * 分片任务执行类
 */
public abstract class ExecutorHandler {

    /**
     *
     * @param shardingContext
     * @param runnigTask
     * @throws ExecutorException
     */
    public abstract void execute(ShardingContext shardingContext, Task runnigTask) throws ExecutorException;

}
