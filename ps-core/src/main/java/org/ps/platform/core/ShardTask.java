package org.ps.platform.core;

import java.io.Serializable;

/**
 * 分片任务
 */
public class ShardTask implements Serializable{

    /**
     * 分片任务ID，主键策略为parentid_shardNumber
     */
    private String id;

    /**
     * 所属Task
     */
    private String parentId;

    /**
     * 对应的分片号
     */
    private int shardNumber;

    /**
     * 额外参数
     */
    private String paramString;

}
