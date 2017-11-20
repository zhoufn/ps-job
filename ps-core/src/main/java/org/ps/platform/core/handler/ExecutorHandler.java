package org.ps.platform.core.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.Task;

/**
 * 分片任务执行类
 */
public abstract class ExecutorHandler {

    public abstract void execute(ShardingContext shardingContext, Task runnigTask);

}
