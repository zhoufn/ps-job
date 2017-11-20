package org.ps.platform.core.handler;

import org.ps.platform.core.Task;
import org.ps.platform.core.zookeeper.ZookeeperHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调度分片基类
 */
public abstract class SchedulerHandler {

    @Autowired
    private ZookeeperHandler handler;

    /**
     * 调度方法
     */
    public void shard(Task waitingTask) {
        try {
            this.scheduler(waitingTask);
        } catch (Exception e) {
            e.printStackTrace();
            waitingTask.setEndTime(System.currentTimeMillis());
            waitingTask.setError(true);
            waitingTask.setErrorMsg("shardTask入库异常。");
            handler.setWaitingTask2Down(waitingTask);
        }
    }

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    protected abstract void scheduler(Task waitingTask) throws Exception;

}
