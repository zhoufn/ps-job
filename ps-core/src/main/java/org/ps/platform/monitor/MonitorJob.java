package org.ps.platform.monitor;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PS平台下用来监控任务的监控器
 */
public class MonitorJob implements SimpleJob{

    private Logger logger = LoggerFactory.getLogger(MonitorJob.class);
    /**
     * 监控作业.
     * 主要职责为监控zookeeper节点task/running节点下正在执行的任务，
     * 重复的调用Task下指定的监控策略（MonitorStrategy）进行监控。
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        ZookeeperHandler handler = (ZookeeperHandler) PsContext.getBean(ZookeeperHandler.class);
        Task task = handler.getRunningTask();
        System.out.println(task.toString());
    }
}
