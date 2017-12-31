package org.ps.service;




import org.ps.domain.ServerStatus;
import org.ps.domain.Task;
import org.ps.enums.TaskType;
import org.ps.handler.ActuatorHandler;

import java.util.List;

/**
 * 首页相关Service接口
 */
public interface IndexService {
    /**
     * 展示服务器监控数据
     * @return 服务监控对象
     * @throws Exception
     */
    public ServerStatus showServerStatus() throws Exception;

    /**
     * 展示等待任务列表
     * @return 待展示任务列表集合
     * @throws Exception
     */
    public List<Task> showWaitingTaskList() throws Exception;

    public List<Task> showTaskList(TaskType taskType) throws Exception;
}
