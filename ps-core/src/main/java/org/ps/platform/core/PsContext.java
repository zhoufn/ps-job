package org.ps.platform.core;

import org.ps.platform.core.annotation.IMonitor;
import org.ps.platform.core.handler.MonitorHandler;
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
public class PsContext implements ApplicationContextAware{

    private static ApplicationContext ac;

    private static ConcurrentHashMap<String, MonitorHandler> monitorHandlerRepository = new ConcurrentHashMap<String, MonitorHandler>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
        Map<String,Object> monitorObjects = ac.getBeansWithAnnotation(IMonitor.class);
        if (monitorObjects!=null && monitorObjects.size()>0) {
            for (Object serviceBean : monitorObjects.values()) {
                if (serviceBean instanceof MonitorHandler){
                    String name = serviceBean.getClass().getAnnotation(IMonitor.class).name();
                    MonitorHandler handler = (MonitorHandler) serviceBean;
                    if (monitorHandlerRepository.get(name) != null) {
                        throw new RuntimeException("Monitor命名冲突。");
                    }
                    monitorHandlerRepository.put(name, handler);
                }
            }
        }
    }



    /**
     * 获取指定类型的Bean
     * @param clazz
     * @return
     */
    public static Object getBean(Class clazz){
        return ac.getBean(clazz);
    }

    /**
     * 根据Monitor注解获取MonitorHandler
     * @param monitrName
     * @return
     */
    public static Object getMonitor(String monitrName){return monitorHandlerRepository.get(monitrName);}
}
