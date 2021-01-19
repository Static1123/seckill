package com.yl.seckill.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Administrator
 */
@Component
public final class SpringApplicationUtil implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {
    private static ApplicationContext context;

    private static boolean applicationReady;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }


    public static <T> T getBean(Class<T> cla) {
        return context.getBean(cla);
    }

    public static <T> Map<String, T> getBeans(Class<T> cla) {
        return context.getBeansOfType(cla);
    }

    public static boolean isApplicationReady() {
        return applicationReady;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            applicationReady = true;
        }

        if (event instanceof ApplicationFailedEvent) {
            applicationReady = false;
        }
    }
}