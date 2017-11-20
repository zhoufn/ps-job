package org.ps.platform.config.jobconfig;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.config.JobConfig;
import org.ps.platform.core.Constant;
import org.ps.platform.core.job.SchedulerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

/**
 * PS平台ExecutorJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'scheduler'")
public class SchedulerJobConfig extends JobConfig {

    @Autowired
    private SchedulerJob job;

    @Override
    protected SimpleJob getJob() {
        return this.job;
    }

    @Override
    protected String getCron() {
        return this.configuration.getSchedulerCron();
    }

    @Override
    protected int getShardingTotalCount() {
        return this.configuration.getSchedulerTotalCount();
    }

    @Override
    protected String getShardingItemParameters() {
        return this.configuration.getSchedulerItemParameters();
    }

    @Override
    protected String getZookeeperNode() {
        return Constant.NODE_SCHEDULER;
    }
}
