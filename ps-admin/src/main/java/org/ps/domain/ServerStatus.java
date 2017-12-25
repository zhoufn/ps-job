package org.ps.domain;

import lombok.Data;

/**
 * 服务监控对象
 */
@Data
public class ServerStatus {
    /**
     * 调度节点平均负载均衡
     */
    private String scheduleOL;
    /**
     * 执行器平均负载均衡
     */
    private String executorOL;
    /**
     * 监控器平均负载均衡
     */
    private String monitorOL;
    /**
     * 任务进度
     */
    private String taskProcess;
    /**
     * 调度器总结点数
     */
    private int scheduleTotalCount;
    /**
     * 执行器总结点数
     */
    private int executorTotalCount;
    /**
     * 监控器总结点数
     */
    private int monitorTotalCount;
    /**
     * 当前执行任务总数
     */
    private int currentTaskCount;
    /**
     * 运行中的调度器总数
     */
    private int scheduleLiveCount;
    /**
     * 运行中的执行器总数
     */
    private int executorLiveCount;
    /**
     * 运行中的监控器总数
     */
    private int monitorLiveCount;
}
