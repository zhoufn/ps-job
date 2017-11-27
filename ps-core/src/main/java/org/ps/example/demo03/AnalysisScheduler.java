package org.ps.example.demo03;

import com.alibaba.fastjson.JSON;
import org.ps.example.demo03.domain.AnalysisShardTaskRepository;
import org.ps.example.demo03.domain.Param;
import org.ps.example.demo03.handler.ScanHandler;
import org.ps.platform.core.ShardTask;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.exception.SchedulerException;
import org.ps.platform.core.handler.SchedulerHandler;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@IScheduler(name = "analysisScheduler")
public class AnalysisScheduler extends SchedulerHandler{

    @Autowired
    private AnalysisShardTaskRepository repository;

    @Autowired
    private ScanHandler scanHandler;

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    @Override
    protected List<ShardTask> scheduler(Task waitingTask) throws SchedulerException {
        Param param = JSON.parseObject(waitingTask.getParamString(),Param.class);
        String srcDir = param.getSrcDir();
        List<ShardTask> shardTasks = this.scanHandler.scan(waitingTask,srcDir,".xml");
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
