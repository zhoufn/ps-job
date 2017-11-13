package org.ps.platform.monitor;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PS平台下用来监控任务的监控器
 */
public class MonitorJob implements SimpleJob{

    private Logger logger = LoggerFactory.getLogger(MonitorJob.class);
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("MonitorJob is running.");
    }
}
