package org.ps.enums;

import org.ps.domain.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务类型枚举类
 */
public enum TaskType{
    WAITING(Constant.NODE_TASK_WAITING), RUNNING(Constant.NODE_TASK_RUNNING), DOWN(Constant.NODE_TASK_DOWN), ALL("all");
    private static Map<String, TaskType> valueMap = new HashMap<>();
    static {
        for(TaskType taskType : TaskType.values()) {
            valueMap.put(taskType.getValue(), taskType);
        }
    }
    private String value;
    TaskType(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
    public static TaskType getByValue(String value){
        return valueMap.get(value);
    }
}
