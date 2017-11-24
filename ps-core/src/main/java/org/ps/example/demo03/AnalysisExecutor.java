package org.ps.example.demo03;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.example.demo02.UnPackageExecutor;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.handler.ExecutorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@IExecutor(name = "analysisExecutor")
public class AnalysisExecutor extends ExecutorHandler{

    @Autowired
    private UnPackageExecutor executor;

    /**
     * @param shardingContext
     * @param runnigTask
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask) {
        ShardTask shardTask = executor.getOneWaitingShardTask(runnigTask,shardingContext.getShardingItem());
        if(shardTask == null){
            return;
        }
        executor.updateShardTaskTime(shardTask,1);

        //TODO 解析

        executor.updateShardTaskTime(shardTask,2);
    }

}
