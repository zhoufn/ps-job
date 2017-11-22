package org.ps.platform.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分片任务
 */
@Data
public class ShardTask implements Serializable{

    /**
     * 分片任务ID，主键策略为parentid_shardNumber
     */
    protected String id;

    /**
     * 所属Task
     */
    protected String parentId;

    /**
     * 对应的分片号
     */
    protected int shardNumber;

    /**
     * 额外参数
     */
    protected String paramString;

    /**
     * 生成时间
     */
    protected Date createTime;

    /**
     * 开始时间
     */
    protected Date beginTime;

    /**
     * 结束时间
     */
    protected Date endTime;

}
