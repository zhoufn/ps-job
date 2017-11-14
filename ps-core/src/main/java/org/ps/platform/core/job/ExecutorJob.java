package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PS平台下用来执行具体分片任务的执行器
 */
public class ExecutorJob implements SimpleJob{

    private Logger logger = LoggerFactory.getLogger(ExecutorJob.class);

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("ExecutorJob is running.");
    }
}
