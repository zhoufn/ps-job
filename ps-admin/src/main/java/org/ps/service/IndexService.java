package org.ps.service;

import org.ps.domain.ServerStatus;

/**
 * 首页需要的Service
 */
public interface IndexService {
    public ServerStatus showServerStatus() throws Exception;
}
