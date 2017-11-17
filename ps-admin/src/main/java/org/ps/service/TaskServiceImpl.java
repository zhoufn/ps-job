package org.ps.service;

import org.ps.handler.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class TaskServiceImpl implements TaskService{

    @Autowired
    private ZookeeperHandler zookeeperHandler;

    @Bean(initMethod = "init")
    public ZookeeperHandler getZookeeperHandler(@Value("${ps.regCenter.serverList}") final String regCenter, @Value("${ps.regCenter.namespace}") final String namespace){
        return new ZookeeperHandler(regCenter,namespace);
    }


}
