package org.ps.platform.monitor;

import org.ps.platform.core.Task;

/**
 * 监控策略类，Monitor会不间断的调用实现类的监控方法。
 */
public abstract class MonitorStrategy {


    /**
     * Monitor周期性调用
     * @return Task是否已完成，true：完成，false：未完成。
     */
    public abstract boolean isDown(Task task);

}
