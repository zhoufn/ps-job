package org.ps.example.demo01;

import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.exception.SchedulerException;
import org.ps.platform.core.handler.SchedulerHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.ps.platform.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@IScheduler(name = "demoScheduler")
public class DemoScheduler extends SchedulerHandler {

    @Autowired
    private DemoShardTaskRepository repository;

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    @Override
    protected List<ShardTask> scheduler(Task waitingTask) throws SchedulerException {
        List<ShardTask> shardTasks = new ArrayList<>();
        DemoShardTask demoShardTask = new DemoShardTask();
        demoShardTask.setId(StringUtils.createUUID());
        demoShardTask.setParentId(waitingTask.getId());
        demoShardTask.setCreateTime(new Date());
        demoShardTask.setShardNumber(0);
        demoShardTask.setParamString("1234567890");
        shardTasks.add(demoShardTask);
        return shardTasks;
    }


    /**
     * 获取JPA接口
     *
     * @return
     */
    @Override
    protected ShardTaskRepository getShardTaskRepository() {
        return this.repository;
    }
}
