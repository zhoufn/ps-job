package org.ps.handler;

import lombok.Getter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * zookeeper操作类
 */
@Configuration
@Component
public class ZookeeperHandler {


    @Autowired
    @Getter
    public ZookeeperClient client;


    @Bean(initMethod = "init",destroyMethod = "destory")
    public ZookeeperClient createBean(@Value("${regCenter.serverList}") final String serverLists,
                                      @Value("${regCenter.namespace}") final String namespace,
                                      @Value("${regCenter.sessionTimeoutMilliseconds}") final int sessionTimeout,
                                      @Value("${regCenter.connectionTimeoutMilliseconds}") final int connectionTimeout){
        return new ZookeeperClient(serverLists,sessionTimeout,connectionTimeout,namespace);
    }


    public class ZookeeperClient{

        private String serverLists;

        private int sessionTimeout;

        private int connectionTimeout;

        private String namespace;

        public ZookeeperClient(String serverLists, int sessionTimeout, int connectionTimeout, String namespace) {
            this.serverLists = serverLists;
            this.sessionTimeout = sessionTimeout;
            this.connectionTimeout = connectionTimeout;
            this.namespace = namespace;
        }

        private CuratorFramework client;

        /**
         * 初始化方法
         */
        public void init() {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(serverLists)
                    .sessionTimeoutMs(sessionTimeout)
                    .connectionTimeoutMs(connectionTimeout)
                    .retryPolicy(new RetryOneTime(1000))
                    .namespace(namespace);
            client = builder.build();
            client.start();
        }

        /**
         * 销毁方法
         */
        public void destory(){
            if(this.client != null){
                this.client.close();
            }
        }

        /**
         * 返回指定节点下的子节点
         * @param path
         * @return
         */
        public List<String> getChildren(String path) throws Exception {
            return this.client.getChildren().forPath(path);
        }

        /**
         * 返回指定节点下的子节点及子节点的数据
         * @param path
         * @return
         */
        public HashMap<String, String> getChildrenWithData(String path) throws Exception {
            HashMap<String, String> map = new HashMap();
            List<String> children = this.getChildren(path);
            if (children != null && children.size() > 0) {
                for (String child : children) {
                    String data = this.getDataForPath(path + "/" +child);
                    if(data != null){
                        map.put(child,data);
                    }
                }
            }
            return map;
        }

        /**
         * 返回指定节点的数据
         * @param path
         * @return
         * @throws Exception
         */
        public String getDataForPath(String path) throws Exception {
            byte[] data = this.client.getData().forPath(path);
            if (data != null) {
                return new String(data);
            }
            return null;
        }

        /**
         * 更新指定节点数据
         * @param path
         * @param data
         * @param flag 如果节点不存在，是否创建节点
         * @return 是否成功
         * @throws Exception
         */
        public boolean updateDataForPath(String path,String data,boolean flag) throws Exception{
            Stat stat = this.client.checkExists().forPath(path);
            if(stat == null){
                if(flag){
                    this.client.create().forPath(path,data.getBytes());
                    return true;
                }else{
                    return false;
                }
            }else{
                this.client.setData().forPath(path,data.getBytes());
                return true;
            }
        }
    }
}
