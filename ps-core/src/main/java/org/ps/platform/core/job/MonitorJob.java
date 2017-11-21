package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.apache.commons.lang3.ClassUtils;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.exception.PSException;
import org.ps.platform.core.handler.MonitorHandler;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.stereotype.Component;

/**
 * PS平台下用来监控任务的监控器
 */
@Component
public class MonitorJob extends AbstractJob{

    /**
     * @param shardingContext 分片上下文
     * @param runnigTask      当前执行中的任务
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask) {
        if(runnigTask == null){
            return;
        }
        try {
            MonitorHandler monitorHandler = (MonitorHandler) PsContext.getMonitor(runnigTask.getMonitor());
            boolean isDown = monitorHandler.monitor(runnigTask);
            //更新进度到zk
            handler.updateRuningTask(runnigTask);
            if(isDown){
                runnigTask.setEndTime(System.currentTimeMillis());
                handler.setRunnintTask2Down(runnigTask);
            }

        } catch (PSException e) {
            e.printStackTrace();
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
