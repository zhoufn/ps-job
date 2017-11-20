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
        if(runnigTask == null){
            return;
        }
        try {
            /**
             * 反射监控类，调用监控方法
             */
            String monitorClazzName = runnigTask.getMonitor();
            Class monitorClazz = ClassUtils.getClass(monitorClazzName);
            MonitorHandler strategy = (MonitorHandler) monitorClazz.newInstance();
            boolean isDown = strategy.isDown(runnigTask);
            if(isDown){ //监控到完成信号时
                runnigTask.setEndTime(System.currentTimeMillis());
                handler.setRunnintTask2Down(runnigTask);
            }
        } catch (Exception e) {
            /**
             * 监控异常时，修改任务错误标识
             */
            runnigTask.setEndTime(System.currentTimeMillis());
            runnigTask.setError(true);
            runnigTask.setErrorMsg(e.getMessage());
            handler.setRunnintTask2Down(runnigTask);
        }
    }
}
