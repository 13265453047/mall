package com.bruce.springbootdemo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Springboot容器初始化过程中
 * 处理顺序
 *
 * @author rcy
 * @version 1.0.0
 * @className: BeanLifecycle
 * @date 2021/9/13 11:24
 */
@Slf4j
// @Component
public class BeanLifecycle implements InitializingBean, CommandLineRunner, BeanPostProcessor {

    /**
     * Constructor(类构造方法) > @Autowired(类中注入的对象） > @PostConstruct > InitializingBean > BeanPostProcessor > CommandLineRunner
     * CommandLineRunner在容器完全启动后执行
     */
    @Autowired
    private RestTemplate restTemplate;

    public BeanLifecycle() {
        log.info(".............AdminMenuInit().......restTemplate:{}", restTemplate);
    }

    @PostConstruct
    public void postConstruct() {
        log.info(".............@PostConstruct.......restTemplate:{}", restTemplate);
    }

    // <bean id="beanLifecycle " class="com.bruce.springbootdemo.init.BeanLifecycle" init-method="initMethod"/>
    public void initMethod() {
        log.info(".............initMethod......");
    }

    public void destroy() {
        log.info(".............destroy......");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("............. @PreDestroy......");
    }

    /**
     * InitializingBean
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(".............InitializingBean ---->  afterPropertiesSet().......restTemplate:{}", restTemplate);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(".............CommandLineRunner ---->  run().......restTemplate:{}", restTemplate);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info(".............BeanPostProcessor ---->  postProcessBeforeInitialization().......restTemplate:{}", restTemplate);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info(".............BeanPostProcessor ---->  postProcessAfterInitialization().......restTemplate:{}", restTemplate);
        return null;
    }

}
