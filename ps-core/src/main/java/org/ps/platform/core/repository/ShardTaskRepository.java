package org.ps.platform.core.repository;

import org.ps.platform.core.ShardTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Date;

public interface ShardTaskRepository<T extends ShardTask,ID extends Serializable> extends CrudRepository<T,ID> {

    long countByParentId(String parentId);

    long countByParentIdAndEndTimeNotNull(String parentId);

    Page<T> findByShardNumberAndParentIdAndEndTimeIsNullOrderByCreateTimeAsc(int shardNumber, String parentId, Pageable pageable);

    @Modifying
    @Query("update ShardTask s set s.endTime=?1 where s.id=?2")
    void modifyEndTimeById(Date endTime,String id);

    @Modifying
    @Query("update ShardTask s set s.beginTime=?1 where s.id=?2")
    void modifyBeginTimeById(Date beginTime,String id);

}