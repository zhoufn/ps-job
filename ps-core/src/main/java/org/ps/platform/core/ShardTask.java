package org.ps.platform.core;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 分片任务
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
        @Index(name = "idx_parentId",columnList = "parentId"),
        @Index(name = "idx_shardNumber",columnList = "shardNumber")
})
public class ShardTask implements Serializable{

    /**
     * 分片任务ID，主键策略为parentid_shardNumber
     */
    @Id
    protected String id;

    /**
     * 所属Task
     */
    @Column(name = "parentId")
    protected String parentId;

    /**
     * 对应的分片号
     */
    @Column(name = "shardNumber")
    protected int shardNumber;

    /**
     * 额外参数
     */
    @Column(name = "paramString")
    protected String paramString;

    /**
     * 生成时间
     */
    @Column(name = "createTime")
    protected Date createTime;

    /**
     * 开始时间
     */
    @Column(name = "beginTime")
    protected Date beginTime;

    /**
     * 结束时间
     */
    @Column(name = "endTime")
    protected Date endTime;

}
