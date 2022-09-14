package com.bruce.hystrix.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GpHystrixCommand {

    /**
     * 超时时间，默认1000毫秒
     *
     * @return
     */
    int timeout() default 1000;

    /**
     * 失败回退方法
     *
     * @return
     */
    String fallbackMethod() default "";

}
