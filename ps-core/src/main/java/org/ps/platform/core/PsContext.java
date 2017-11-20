package org.ps.platform.core;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import org.ps.platform.core.annotation.IExecutor;
import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.annotation.IScheduler;
import org.ps.platform.core.handler.ExecutorHandler;
import org.ps.platform.core.handler.MonitorHandler;
import org.ps.platform.core.handler.SchedulerHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PS上下文
 */
@Component
public class PsContext implements ApplicationContextAware {

    private static ApplicationContext ac;

    private static ConcurrentHashMap<String,String> nameRepository = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, MonitorHandler> monitorHandlerRepository = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, SchedulerHandler> schedulerHandlerRepository = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, ExecutorHandler> executorHandlerRepository = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
        /**
         * 加载自定义监控
         */
        Map<String, Object> monitorObjects = ac.getBeansWithAnnotation(IMonitor.class);
        if (monitorObjects != null && monitorObjects.size() > 0) {
            for (Object serviceBean : monitorObjects.values()) {
                if (serviceBean instanceof MonitorHandler) {
                    String name = serviceBean.getClass().getAnnotation(IMonitor.class).name();
                    MonitorHandler handler = (MonitorHandler) serviceBean;
                    if (monitorHandlerRepository.get(name) != null || nameRepository.get(name) != null) {
                        throw new RuntimeException("Monitor命名冲突。");
                    }
                    monitorHandlerRepository.put(name, handler);
                    nameRepository.put(name,name);
                }
            }
        }

        /**
         * 加载自定义调度
         */
        Map<String,Object> schedulerObjects = ac.getBeansWithAnnotation(IScheduler.class);
        if(schedulerObjects != null && schedulerObjects.size() > 0){
            for(Object serviceBean : schedulerObjects.values()){
                if(serviceBean instanceof SchedulerHandler){
                    String name = serviceBean.getClass().getAnnotation(IScheduler.class).name();
                    SchedulerHandler handler = (SchedulerHandler) serviceBean;
                    if(schedulerHandlerRepository.get(name) != null || nameRepository.get(name) != null){
                        throw new RuntimeException("Scheduler命名冲突。");
                    }
                    schedulerHandlerRepository.put(name,handler);
                    nameRepository.put(name,name);
                }
            }
        }

        /**
         * 加载自定义执行器
         */
        Map<String,Object> executorObjects = ac.getBeansWithAnnotation(IExecutor.class);
        if(executorObjects != null && executorObjects.size() > 0){
            for(Object serviceBean : executorObjects.values()){
                if(serviceBean instanceof ExecutorHandler){
                    String name = serviceBean.getClass().getAnnotation(IExecutor.class).name();
                    ExecutorHandler handler = (ExecutorHandler) serviceBean;
                    if(executorHandlerRepository.get(name) != null || nameRepository.get(name) != null){
                        throw new RuntimeException("Executor命名冲突。");
                    }
                    executorHandlerRepository.put(name,handler);
                    nameRepository.put(name,name);
                }
            }
        }
    }

    /**
     * 获取指定类型的Bean
     *
     * @param clazz
     * @return
     */
    public static Object getBean(Class clazz) {
        return ac.getBean(clazz);
    }

    /**
     * 根据Monitor注解获取MonitorHandler
     *
     * @param monitrName
     * @return
     */
    public static Object getMonitor(String monitrName) {
        return monitorHandlerRepository.get(monitrName);
    }

    /**
     * 根据IScheduler注解获取SchedulerHandler
     * @param schedulerName
     * @return
     */
    public static Object getScheduler(String schedulerName){
        return schedulerHandlerRepository.get(schedulerName);
    }

    /**
     * 根据IExecutor注解获取ExecutorHandler
     * @param executorName
     * @return
     */
    public static Object getExecutor(String executorName){return executorHandlerRepository.get(executorName);}
}
