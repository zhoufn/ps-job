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

    /**
     * 更新服务运行状态
     * @param taskId 任务ID
     * @param paused 是否暂停标志位
     * @return
     * @throws Exception
     */
    public boolean updateTask(String taskId, boolean paused) throws Exception;
}
