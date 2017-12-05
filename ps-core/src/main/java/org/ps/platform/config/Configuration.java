package org.ps.platform.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 平台配置
 */
@Configurable
@Data
@Component
public class Configuration {
    @Value("${server.port}") private String serverPort;
    @Value("${server.role}") private String serverRole;
    @Value("${regCenter.serverList}") private String regCenterServerList;
    @Value("${regCenter.namespace}") private String regCenterNameSpace;
    @Value("${monitor.cron}") private String monitorCron;
    @Value("${scheduler.cron}") private String schedulerCron;
    @Value("${executor.cron}") private String executorCron;
    @Value("${monitor.monitorTotalCount}") private int monitorTotalCount;
    @Value("${scheduler.shardingTotalCount}") private int schedulerTotalCount;
    @Value("${executor.executorTotalCount}") private int executorTotalCount;
    @Value("${monitor.monitorItemParameters}") private String monitorItemParameters;
    @Value("${scheduler.shardingItemParameters}") private String schedulerItemParameters;
    @Value("${executor.executorItemParameters}") private String executorItemParameters;
}
