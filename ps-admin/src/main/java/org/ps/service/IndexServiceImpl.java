package org.ps.service;

import org.ps.domain.ServerStatus;
import org.ps.handler.ActuatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    ActuatorHandler ah;
    @Override
    public ServerStatus showServerStatus() throws Exception {
        return ah.getServerStatus();
    }
}
