package org.ps.platform.config.jobconfig;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.ps.platform.config.JobConfig;
import org.ps.platform.core.Constant;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.ps.platform.executor.ExecutorJob;
import org.ps.platform.scheduler.SchedulerJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * PS平台SchedulerJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'executor'")
public class ExecutorJobConfig extends JobConfig {

    @Value("${executor.cron}")
    private String cron;

    @Value("${executor.executorTotalCount}")
    private int executorTotalCount;

    @Value("${executor.executorItemParameters}")
    private String executorItemParameters;

    @Override
    protected SimpleJob getJob() {
        return new ExecutorJob();
    }

    @Override
    protected String getCron() {
        return this.cron;
    }

    @Override
    protected int getShardingTotalCount() {
        return this.executorTotalCount;
    }

    @Override
    protected String getShardingItemParameters() {
        return this.executorItemParameters;
    }

    @Override
    protected String getZookeeperNode() {
       return Constant.NODE_EXECUTOR;
    }
}
