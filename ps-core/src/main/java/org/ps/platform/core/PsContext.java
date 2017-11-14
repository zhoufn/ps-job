package org.ps.platform.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * PS上下文
 */
@Component
public class PsContext implements ApplicationContextAware{

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * 获取指定类型的Bean
     * @param clazz
     * @return
     */
    public static Object getBean(Class clazz){
        return ac.getBean(clazz);
    }

}
