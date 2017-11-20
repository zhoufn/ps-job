package org.ps.example.demo01;

import org.ps.platform.core.Task;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.handler.MonitorHandler;
import org.springframework.stereotype.Component;

@IMonitor(name = "demoMonitor")
@Component
public class Demo01MonitorHandler extends MonitorHandler {
    /**
     * Monitor周期性调用
     *
     * @param task
     * @return Task是否已完成，true：完成，false：未完成。
     */
    @Override
    public boolean isDown(Task task) {
        System.out.println("******************");
        if((System.currentTimeMillis() - task.getCreateTime() > 100000)){
            return true;
        }
        return false;
    }
}
