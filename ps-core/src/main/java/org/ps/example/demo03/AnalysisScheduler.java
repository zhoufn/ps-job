package org.ps.example.demo03;

import com.alibaba.fastjson.JSON;
import org.ps.example.demo03.domain.Param;
import org.ps.example.demo03.handler.ScanHandler;
import org.ps.platform.config.Configuration;
import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.exception.SchedulerException;
import org.ps.platform.core.handler.SchedulerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@IScheduler(name = "analysisScheduler")
public class AnalysisScheduler extends SchedulerHandler{

    @Autowired
    private ScanHandler scanHandler;
    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    @Override
    protected void scheduler(Task waitingTask) throws SchedulerException {
        Param param = JSON.parseObject(waitingTask.getParamString(),Param.class);
        String srcDir = param.getSrcDir();
        this.scanHandler.scan(waitingTask,srcDir,".xml");
    }
}
