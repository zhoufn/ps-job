package org.ps.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class Config {

    @Value("${ps.regCenter.serverList}") private String regCenterServerList;

    @Value("${ps.regCenter.namespace}") private String regCenterNameSpace;

    @Value("${ps.zookeeper.node.scheduler}") private String zkNodeScheduler;

    @Value("${ps.zookeeper.node.monitor}") private String zkNodeMonitor;

    @Value("${ps.zookeeper.node.executor}") private String zkNodeExecutor;

    @Value("${ps.zookeeper.node.task}") private String zkNodeTask;

    @Value("${ps.zookeeper.node.task.running}") private String zkNodeRunningTask;

    @Value("${ps.zookeeper.node.task.waiting}") private String zkNodeWaitintTask;

    @Value("${ps.zookeeper.node.task.down}") private String zkNodeDownTask;

}
