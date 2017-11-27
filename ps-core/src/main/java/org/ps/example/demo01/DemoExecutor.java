package org.ps.example.demo01;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.handler.ExecutorHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@IExecutor(name = "demoExecutor")
public class DemoExecutor extends ExecutorHandler{

    @Autowired
    private DemoShardTaskRepository repository;

    /**
     * @param shardingContext 分片上下文
     * @param runningTask     运行任务
     * @param shardTask       分片任务
     */
    @Override
    public void execute(ShardingContext shardingContext, Task runningTask, ShardTask shardTask) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
