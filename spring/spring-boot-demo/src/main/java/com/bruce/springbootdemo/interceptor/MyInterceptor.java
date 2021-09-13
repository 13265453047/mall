package com.bruce.springbootdemo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rcy
 * @version 1.0.0
 * @className: MyInterceptor
 * @date 2021/9/13 11:29
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        return true;
    }

}
