package org.ps.handler;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作zookeeper的Handler
 */
public class ZookeeperHandler {

    private CuratorFramework client;

    private String serverList;

    private String namespace;

    public ZookeeperHandler(String serverList, String namespace) {
        this.serverList = serverList;
        this.namespace = namespace;
    }

    /**
     * 初始化方法
     */
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(this.serverList)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(this.namespace);
        builder.sessionTimeoutMs(2000);
        builder.connectionTimeoutMs(3000);
        client = builder.build();
        client.start();
    }

    /**
     * 返回指定节点下的子节点
     * @param path
     * @return
     */
    public List<String> getChildren(String path){
        List<String> children = null;
        try{
            children = this.client.getChildren().forPath(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return children;
    }

}
