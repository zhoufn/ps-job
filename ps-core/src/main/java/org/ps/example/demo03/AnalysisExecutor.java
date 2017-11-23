package org.ps.example.demo03;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.handler.ExecutorHandler;
import org.springframework.stereotype.Component;

@Component
@IExecutor(name = "analysisExecutor")
public class AnalysisExecutor extends ExecutorHandler{

    /**
     * @param shardingContext
     * @param runnigTask
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runnigTask) {

    }
}
