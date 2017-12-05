package org.ps.platform.env;

import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.annotation.IScheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PS上下文
 */
@Component
public class PsContext implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * 根据Monitor注解获取MonitorHandler
     *
     * @param monitrName
     * @return
     */
    public static Object getMonitor(String monitrName) {
        return getBeanWithAnnotationAndName(IMonitor.class,monitrName);
    }

    /**
     * 根据IScheduler注解获取SchedulerHandler
     * @param schedulerName
     * @return
     */
    public static Object getScheduler(String schedulerName){
        return getBeanWithAnnotationAndName(IScheduler.class,schedulerName);
    }

    /**
     * 根据IExecutor注解获取ExecutorHandler
     * @param executorName
     * @return
     */
    public static Object getExecutor(String executorName){
        return getBeanWithAnnotationAndName(IExecutor.class,executorName);
    }

    /**
     * 根据注解类型和注解name熟悉获取Bean
     * @param clazz
     * @param name
     * @return
     */
    private static Object getBeanWithAnnotationAndName(Class clazz,String name){
        Map<String,Object> beans = ac.getBeansWithAnnotation(clazz);
        for(String key : beans.keySet()){
            if(name.equals(key)){
                return beans.get(key);
            }
        }
        return null;
    }
}
