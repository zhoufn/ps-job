package org.ps.platform.core.annotation;

import java.lang.annotation.*;

/**
 * 用户自定义监控类注解，唯一标识name
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IMonitor {
    String name() default "";
}
