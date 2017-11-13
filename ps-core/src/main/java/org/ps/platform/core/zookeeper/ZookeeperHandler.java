package org.ps.platform.core.zookeeper;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.zookeeper.data.Stat;
import org.ps.platform.core.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * zookeeper操作类
 */
@Component
public class ZookeeperHandler {

    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    /**
     * 初始化Task节点
     */
    public void initTaskTree() {
        String runningPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_RUNNING;
        String waitingPath = "/" + Constant.NODE_TASK + "/" + Constant.NODE_TASK_WAITING;
        this.createIfNotExist(runningPath);
        this.createIfNotExist(waitingPath);
    }

    /**
     * 路径不存在的话创建
     *
     * @param path
     */
    public void createIfNotExist(String path) {
        try {
            Stat stat = registryCenter.getClient().checkExists().forPath(path);
            if (stat == null) {
                registryCenter.getClient().create().creatingParentsIfNeeded().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
