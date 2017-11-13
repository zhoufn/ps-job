package org.ps.platform.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.ps.platform.core.Constant;
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
public class SchedulerJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public SchedulerJob schedulerJob() {
        return new SchedulerJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SchedulerJob schedulerJob, @Value("${scheduler.cron}") final String cron, @Value("${scheduler.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${scheduler.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(schedulerJob, regCenter, getLiteJobConfiguration(schedulerJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                Constant.NODE_SCHEDULER, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
