package org.ps.platform.core.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.apache.commons.lang3.ClassUtils;
import org.ps.platform.core.PsContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.handler.SchedulerHandler;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.stereotype.Component;

/**
 * 平台下用来执行分片策略的调度器
 */
@Component
public class SchedulerJob extends AbstractJob{

    /**
     * @param shardingContext 分片上下文
     * @param runnigTask      当前执行中的任务
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask) {
        /**
         * 当前有任务执行时等待
         */
        if(runnigTask != null){
            return;
        }
        Task waitingJob = handler.getOneWaitingTask();
        if(waitingJob == null){//不存在等待任务时
            return;
        }
        /**
         * 获取分片类，执行分片方法
         */
        try{
            SchedulerHandler schedulerHandler = (SchedulerHandler) PsContext.getScheduler(waitingJob.getScheduler());
            schedulerHandler.shard(waitingJob);
        }catch (Exception e){
            e.printStackTrace();
            waitingJob.setEndTime(System.currentTimeMillis());
            waitingJob.setError(true);
            waitingJob.setErrorMsg("分片策略执行异常。");
            handler.setWaitingTask2Down(waitingJob);
        }
        /**
         * 成功后将此任务转为执行任务
         */
        handler.setWaitingTask2Running(waitingJob);
    }
}
