package org.ps.example.demo01;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.handler.SchedulerHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@IScheduler(name = "demoScheduler")
@Component
public class Demo01SchedulerHandler extends SchedulerHandler {

    @Resource
    private DataSource dataSource;

    /**
     * 用户自定义分片策略
     *
     * @param waitingTask
     * @return
     */
    @Override
    protected void scheduler(Task waitingTask) throws Exception {
        System.out.println(dataSource);
    }
}
