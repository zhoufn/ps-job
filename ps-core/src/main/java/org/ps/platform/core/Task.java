package org.ps.platform.core;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
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
    @NotNull private String id;

    /**
     * 创建时间
     */
    @NotNull private long createTime;

    /**
     * 调度类
     */
    @NotNull private String scheduler;

    /**
     * 监控类
     */
    @NotNull private String monitor;

    /**
     * 执行类
     */
    @NotNull private String executor;

    /**
     * 附加参数
     */
    private String paramString;

    /**
     * 优先级
     */
    @NotNull private int sort;

    /**
     * 结束时间
     */
    private long endTime;


}
