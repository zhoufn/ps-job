package org.ps.platform.core.handler;

import org.ps.platform.core.repository.ShardTaskRepository;

/**
 * Handler基类
 */
public abstract class Handler {

    /**
     * 获取JPA接口
     * @return
     */
    protected abstract ShardTaskRepository getShardTaskRepository();

}
