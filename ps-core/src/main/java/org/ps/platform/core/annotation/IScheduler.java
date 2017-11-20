package org.ps.platform.core.annotation;

import java.lang.annotation.*;

/**
 * 用户自定义调度类，唯一标识name
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IScheduler {
    String name() default "";
}
