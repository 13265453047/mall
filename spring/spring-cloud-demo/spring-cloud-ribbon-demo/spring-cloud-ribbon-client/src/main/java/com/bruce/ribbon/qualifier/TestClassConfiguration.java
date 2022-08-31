package com.bruce.ribbon.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rcy
 * @version 1.1.0
 * @className: TestClassConfiguration
 * @date 2022-08-31
 */
@Configuration
public class TestClassConfiguration {

    @Bean
    // @Qualifier
    TestClass testClass1() {
        return new TestClass("testClass1");
    }

    @Bean
    @Qualifier
    TestClass testClass2() {
        return new TestClass("testClass2");
    }

}
