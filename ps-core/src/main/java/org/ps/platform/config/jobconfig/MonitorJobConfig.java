package org.ps.platform.config.jobconfig;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.config.JobConfig;
import org.ps.platform.core.Constant;
import org.ps.platform.monitor.MonitorJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

/**
 * PS平台MonitorJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'monitor'")
public class MonitorJobConfig extends JobConfig {

    @Value("${monitor.cron}")
    private String cron;

    @Value("${monitor.monitorTotalCount}")
    private int monitorTotalCount;

    @Value("${monitor.monitorItemParameters}")
    private String monitorItemParameters;

    @Override
    protected SimpleJob getJob() {
        return new MonitorJob();
    }

    @Override
    protected String getCron() {
        return this.cron;
    }

    @Override
    protected int getShardingTotalCount() {
        return this.monitorTotalCount;
    }

    @Override
    protected String getShardingItemParameters() {
        return this.monitorItemParameters;
    }

    @Override
    protected String getZookeeperNode() {
        return Constant.NODE_MONITOR;
    }
}
