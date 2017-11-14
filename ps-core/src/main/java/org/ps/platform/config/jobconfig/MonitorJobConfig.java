package org.ps.platform.config.jobconfig;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.config.JobConfig;
import org.ps.platform.core.Constant;
import org.ps.platform.core.job.MonitorJob;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

/**
 * PS平台MonitorJob的配置类
 */
@Configuration
@ConditionalOnExpression("'${server.role}' == 'monitor'")
public class MonitorJobConfig extends JobConfig {

    @Override
    protected SimpleJob getJob() {
        return new MonitorJob();
    }

    @Override
    protected String getCron() {
        return this.configuration.getMonitorCron();
    }

    @Override
    protected int getShardingTotalCount() {
        return this.configuration.getMonitorTotalCount();
    }

    @Override
    protected String getShardingItemParameters() {
        return this.configuration.getMonitorItemParameters();
    }

    @Override
    protected String getZookeeperNode() {
        return Constant.NODE_MONITOR;
    }
}
