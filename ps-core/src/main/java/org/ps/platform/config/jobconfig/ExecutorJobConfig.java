package org.ps.platform.config.jobconfig;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.config.JobConfig;
import org.ps.platform.core.Constant;
import org.ps.platform.core.job.ExecutorJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

/**
 * PS平台SchedulerJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'executor'")
public class ExecutorJobConfig extends JobConfig {

    @Override
    protected SimpleJob getJob() {
        return new ExecutorJob();
    }

    @Override
    protected String getCron() {
        return this.configuration.getExecutorCron();
    }

    @Override
    protected int getShardingTotalCount() {
        return this.configuration.getExecutorTotalCount();
    }

    @Override
    protected String getShardingItemParameters() {
        return this.configuration.getExecutorItemParameters();
    }

    @Override
    protected String getZookeeperNode() {
       return Constant.NODE_EXECUTOR;
    }
}
