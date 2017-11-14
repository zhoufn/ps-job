package org.ps.platform.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@Configurable
public abstract class JobConfig {

    @Resource
    protected ZookeeperRegistryCenter regCenter;

    @Resource
    protected JobEventConfiguration jobEventConfiguration;

    @Resource
    protected ZookeeperHandler zookeeperHandler;


    protected abstract SimpleJob getJob();

    protected abstract String getCron();

    protected abstract int getShardingTotalCount();

    protected abstract String getShardingItemParameters();

    protected abstract String getZookeeperNode();

    /**
     * 启动入口
     *
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler() {
        //初始化Task结构
        zookeeperHandler.initTaskTree();
        SimpleJob simpleJob = this.getJob();
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), this.getCron(), this.getShardingTotalCount(), this.getShardingItemParameters()), jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                this.getZookeeperNode(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }

}
