package org.ps.platform.env;

import com.dangdang.ddframe.job.util.env.IpUtils;
import org.ps.platform.config.Configuration;
import org.ps.platform.core.Constant;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 平台环境
 */
@Component
public class PsEnvironment {

    @Autowired
    private Configuration configuration;

    @Autowired
    private ZookeeperHandler zookeeperHandler;

    private String runningPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_RUNNING;

    private String waitingPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_WAITING;

    private String downPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_DOWN;

    private String envPath = "/" + Constant.NODE_ENVIRONMENT ;

    /**
     * 环境注册
     */
    public void register(){
        this.registerTask();
        this.registerEnv();
    }

    /**
     * 注册Task
     */
    private void registerTask(){
        zookeeperHandler.createIfNotExist(runningPath,null);
        zookeeperHandler.createIfNotExist(waitingPath,null);
        zookeeperHandler.createIfNotExist(downPath,null);
    }

    /**
     * 注册PS环境
     */
    private void registerEnv(){
        String self = envPath + "/" + IpUtils.getIp();
        zookeeperHandler.createIfNotExist(self + Constant.PATH_SEPARATOR + this.configuration.getServerPort(),this.configuration);
    }
}
