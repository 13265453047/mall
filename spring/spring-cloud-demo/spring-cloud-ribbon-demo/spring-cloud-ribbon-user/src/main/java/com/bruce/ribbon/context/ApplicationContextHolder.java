package com.bruce.ribbon.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

/**
 *
 **/
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static MessageSource getMessageSource() {
        if (applicationContext == null) {
            return null;
        } else {
            return applicationContext.getBean(MessageSource.class);
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
