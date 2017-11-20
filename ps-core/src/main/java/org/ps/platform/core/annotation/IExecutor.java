package org.ps.platform.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义执行类，name为唯一标识
 */
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IExecutor {
}
