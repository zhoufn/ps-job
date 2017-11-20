package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.lang3.ClassUtils;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.ps.platform.core.handler.MonitorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PS平台下用来监控任务的监控器
 */
public class MonitorJob extends AbstractJob{

    /**
     * @param shardingContext 分片上下文
     * @param runnigTask      当前执行中的任务
     */
    @Override
    public void execute(ZookeeperHandler handler,ShardingContext shardingContext, Task runnigTask) {
        String monitorClazzName = runnigTask.getMonitor();
        Class monitorClazz = null;
        MonitorHandler strategy = null;
        try {
            monitorClazz = ClassUtils.getClass(monitorClazzName);
            strategy = (MonitorHandler) monitorClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(strategy == null){
            return;
        }
        boolean isDown = strategy.isDown(runnigTask);
        if(isDown){
            runnigTask.setEndTime(System.currentTimeMillis());
            handler.setRunnintTask2Down(runnigTask);
        }
    }
}
