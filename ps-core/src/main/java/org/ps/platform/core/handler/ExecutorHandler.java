package org.ps.platform.core.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.exception.ExecutorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 分片任务执行类
 */
public abstract class ExecutorHandler extends Handler {

    /**
     *
     * @param shardingContext
     * @param runnigTask
     * @throws ExecutorException
     */
    public void execute(ShardingContext shardingContext, Task runnigTask){
        Page<ShardTask> page = this.getShardTaskRepository().findByShardNumberAndParentIdAndEndTimeIsNullOrderByCreateTimeAsc(shardingContext.getShardingItem(),runnigTask.getId(),new PageRequest(0,1));
        if(page == null || page.getTotalElements() == 0){
            return;
        }
        ShardTask shardTask = page.getContent().get(0);
        if(shardTask == null){
            return;
        }
        System.out.println(this);
        System.out.println("**"+this.getShardTaskRepository());
        shardTask.setBeginTime(new Date());
        this.getShardTaskRepository().modifyBeginTimeById(shardTask.getBeginTime(),shardTask.getId());
        this.execute(shardingContext,runnigTask,shardTask);
        shardTask.setEndTime(new Date());
        this.getShardTaskRepository().modifyEndTimeById(shardTask.getEndTime(),shardTask.getId());
    }

    /**
     * @param shardingContext 分片上下文
     * @param runningTask 运行任务
     * @param shardTask 分片任务
     */
    public abstract void execute(ShardingContext shardingContext, Task runningTask, ShardTask shardTask);

}
