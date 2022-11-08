package com.bruce.springbootdemo.aspect;

import com.alibaba.fastjson.JSON;
import com.bruce.springbootdemo.annotation.LogAspect;
import com.bruce.springbootdemo.util.IPUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 切面注解
 *
 * @author rcy
 * @version 1.1.0
 * @className: AnnotationControllerLogAop
 * @date 2022-09-16
 */
@Aspect
@Component
@Slf4j
public class AnnotationControllerLogAop {

    // 此方式只能拦截方法层面添加注解
    @Pointcut("@annotation(com.bruce.springbootdemo.annotation.LogAspect)")
    public void log() {
    }


    @Around("log()&&@annotation(logAspect)")
    public Object handle(ProceedingJoinPoint proceedingJoinPoint, LogAspect logAspect) throws Throwable {

        // 获取拦截方法注解
        String value = logAspect.value();

        // 获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        // 获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        // 获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();

        // 获取controller的请求属性数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();

        printRequestLog(request, className, methodName, args);
        long start = System.currentTimeMillis();
        // 继续执行方法逻辑
        Object returnObj = proceedingJoinPoint.proceed();
        printResponseLog(request, className, methodName, returnObj, System.currentTimeMillis() - start);
        return returnObj;
    }


    private void printRequestLog(HttpServletRequest request, String clazzName, String methodName, Object[] args) throws JsonProcessingException {
        log.debug("Request URL: [{}], URI: [{}], Request Method: [{}], IP: [{}]",
                request.getRequestURL(),
                request.getRequestURI(),
                request.getMethod(),
                IPUtils.getIpAddr(request));

        if (args == null || !log.isDebugEnabled()) {
            return;
        }

        boolean shouldNotLog = false;
        for (Object arg : args) {
            if (arg == null ||
                    arg instanceof HttpServletRequest ||
                    arg instanceof HttpServletResponse ||
                    arg instanceof MultipartFile ||
                    arg.getClass().isAssignableFrom(MultipartFile[].class)) {
                shouldNotLog = true;
                break;
            }
        }

        if (!shouldNotLog) {
            String requestBody = JSON.toJSONString(args);
            log.debug("{}.{} Parameters: [{}]", clazzName, methodName, requestBody);
        }
    }

    private void printResponseLog(HttpServletRequest request, String className, String methodName, Object returnObj, long usage) throws JsonProcessingException {
        if (log.isDebugEnabled()) {
            String returningData = null;
            if (returnObj != null) {
                if (returnObj.getClass().isAssignableFrom(byte[].class)) {
                    returningData = "Binary data";
                } else {
                    returningData = JSON.toJSONString(returnObj);
                }
            }
            log.debug("{}.{} Response: [{}], usage: [{}]ms", className, methodName, returningData, usage);
        }
    }

}
