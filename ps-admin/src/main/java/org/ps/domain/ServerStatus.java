package org.ps.domain;

import lombok.Data;

@Data
public class ServerStatus {
    private String scheduleOL;
    private String executorOL;
    private String monitorOL;
    private String taskProcess;
    private int scheduleTotalCount;
    private int executorTotalCount;
    private int monitorTotalCount;
    private String currentTask;
    private int scheduleLiveCount;
    private int executorLiveCount;
    private int monitorLiveCount;
}
