package org.ps.service;
import org.ps.domain.Task;

/**
 * Task服务类
 */
public interface TaskService {

    /**
     * 新增任务
     * @param task   任务对象
     * @return
     */
    public boolean addTask(Task task) throws Exception;
}
