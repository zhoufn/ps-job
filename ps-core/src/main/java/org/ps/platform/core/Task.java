package org.ps.platform.core;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 平台核心类
 */
@Data
@RequiredArgsConstructor
public class Task implements Serializable{

    /**
     * id,uuid
     */
    private String id;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 调度类
     */
    private String scheduler;

    /**
     * 监控类
     */
    private String monitor;

    /**
     * 执行类
     */
    private String executor;

    /**
     * 附加参数
     */
    private String paramString;

    /**
     * 优先级
     */
    private int sort;


}
