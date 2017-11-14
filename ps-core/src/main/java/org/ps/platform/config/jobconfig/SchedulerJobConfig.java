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
import org.ps.platform.monitor.MonitorJob;
import org.ps.platform.scheduler.SchedulerJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * PS平台ExecutorJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'scheduler'")
public class SchedulerJobConfig extends JobConfig {

    @Value("${scheduler.cron}")
    private String cron;

    @Value("${scheduler.shardingTotalCount}")
    private int shardingTotalCount;

    @Value("${scheduler.shardingItemParameters}")
    private String shardingItemParameters;

    @Override
    protected SimpleJob getJob() {
        return new SchedulerJob();
    }

    @Override
    protected String getCron() {
        return this.cron;
    }

    @Override
    protected int getShardingTotalCount() {
        return this.shardingTotalCount;
    }

    @Override
    protected String getShardingItemParameters() {
        return this.shardingItemParameters;
    }

    @Override
    protected String getZookeeperNode() {
        return Constant.NODE_SCHEDULER;
    }
}
