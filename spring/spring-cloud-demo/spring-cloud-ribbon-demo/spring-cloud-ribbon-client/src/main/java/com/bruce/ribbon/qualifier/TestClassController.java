package com.bruce.ribbon.qualifier;

import com.bruce.ribbon.context.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author rcy
 * @version 1.1.0
 * @className: TestClassController
 * @date 2022-08-31
 */
@RestController
public class TestClassController {

    /**
     * @Qualifier : 注解作为标记注解
     * 在注入的时候选择带有此标记的bean进行注入
     * @LoadBalanced 注解中就带有 @Qualifier 注解，所以 LoadBalancerAutoConfiguration 中的 restTemplates
     */
    @Qualifier
    @Autowired(required = false)
    private List<TestClass> testClassList = Collections.emptyList();

    @GetMapping("get")
    public List<TestClass> getTestClassList() {

        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        Object testClass1 = applicationContext.getBean("testClass1");
        Object testClass2 = applicationContext.getBean("testClass2");

        System.out.println("testClass1 = " + testClass1);
        System.out.println("testClass2 = " + testClass2);

        return testClassList;
    }

}
