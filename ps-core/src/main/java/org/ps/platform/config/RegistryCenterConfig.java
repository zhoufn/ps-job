package org.ps.platform.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心配置
 */
@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class RegistryCenterConfig {
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace,
            @Value("${regCenter.sessionTimeoutMilliseconds}") final int sessionTimeout,
            @Value("${regCenter.connectionTimeoutMilliseconds}") final int connectionTimeout) {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverList, namespace);
        configuration.setConnectionTimeoutMilliseconds(connectionTimeout);
        configuration.setSessionTimeoutMilliseconds(sessionTimeout);
        return new ZookeeperRegistryCenter(configuration);
    }
}
