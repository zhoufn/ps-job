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
public class ExecutorJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public ExecutorJob executorJob() {
        return new ExecutorJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final ExecutorJob executorJob, @Value("${executor.cron}") final String cron, @Value("${executor.executorTotalCount}") final int shardingTotalCount,
                                           @Value("${executor.executorItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(executorJob, regCenter, getLiteJobConfiguration(executorJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                Constant.NODE_EXECUTOR, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
