package org.ps.handler.actuator;

import lombok.Data;

import java.util.List;

@Data
public class ActuatorEntity {

    private String key;

    private String value;

    private List<ActuatorEntity> children;

}
