package com.bruce.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //类/接口
@Retention(RetentionPolicy.RUNTIME)
@Component //被spring进行扫描？
public @interface RpcService {

    /**
     * 拿到服务接口
     *
     * @return
     */
    Class<?> value();

    /**
     * 服务版本号
     *
     * @return
     */
    String version() default "";

}
