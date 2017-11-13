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
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.ps.platform.monitor.MonitorJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * PS平台MonitorJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'monitor'")
public class MonitorJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public MonitorJob monitorJob() {
        return new MonitorJob();
    }

    @Resource
    private ZookeeperHandler zookeeperHandler;

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MonitorJob monitorJob, @Value("${monitor.cron}") final String cron, @Value("${monitor.monitorTotalCount}") final int shardingTotalCount,
                                           @Value("${monitor.monitorItemParameters}") final String shardingItemParameters) {
        zookeeperHandler.initTaskTree();
        return new SpringJobScheduler(monitorJob, regCenter, getLiteJobConfiguration(monitorJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                Constant.NODE_MONITOR, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
