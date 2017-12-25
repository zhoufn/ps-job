package org.ps.service;

import org.ps.domain.ServerStatus;

/**
 * 首页相关Service接口
 */
public interface IndexService {
    /**
     * 展示服务器监控数据
     * @return 服务监控对象
     * @throws Exception
     */
    public ServerStatus showServerStatus() throws Exception;
}
