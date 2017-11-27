package org.ps.platform.core.handler;

import org.ps.platform.config.Configuration;
import org.ps.platform.core.repository.ShardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handler基类
 */
public abstract class Handler {

    @Autowired
    protected Configuration configuration;

    /**
     * 获取JPA接口
     * @return
     */
    protected abstract ShardTaskRepository getShardTaskRepository();

}
