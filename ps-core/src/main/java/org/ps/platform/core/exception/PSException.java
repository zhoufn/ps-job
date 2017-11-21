package org.ps.platform.core.exception;

import lombok.Data;

/**
 * 平台异常
 */
@Data
public abstract class PSException extends Exception{

    protected String message;

}
