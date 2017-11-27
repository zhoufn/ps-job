package org.ps.handler;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.ps.domain.Config;
import org.ps.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作zookeeper的Handler
 */
@Repository
public class ZookeeperHandler {

    @Autowired
    private Config config;

    private CuratorFramework client;

    public ZookeeperHandler() {

    }

    /**
     * 初始化方法
     */
    public void init() {
        if (client == null) {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(this.config.getRegCenterServerList())
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .namespace(this.config.getRegCenterNameSpace());
            builder.sessionTimeoutMs(2000);
            builder.connectionTimeoutMs(3000);
            client = builder.build();
            client.start();
        }
    }

    /**
     * 返回指定节点下的子节点
     *
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        this.init();
        List<String> children = null;
        try {
            children = this.client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    /**
     * 新增一个等待任务
     * @param task
     */
    public void addWaitingTask(Task task) {
        this.init();
        try {
            this.client.create().forPath("/" + this.config.getZkNodeTask() + "/" + this.config.getZkNodeWaitintTask() + "/" + task.getId(), JSON.toJSONString(task).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
