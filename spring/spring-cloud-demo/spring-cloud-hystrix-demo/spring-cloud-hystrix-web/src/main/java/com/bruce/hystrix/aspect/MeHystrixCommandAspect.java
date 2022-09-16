package com.bruce.hystrix.aspect;

import com.bruce.hystrix.annotation.MeHystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.*;

@Component
@Aspect
@Slf4j
public class MeHystrixCommandAspect {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Pointcut("@annotation(com.bruce.hystrix.annotation.MeHystrixCommand)")
    public void hystrixCommon() {
    }

    @Around("hystrixCommon()&&@annotation(hystrixCommand)")
    public Object handle(ProceedingJoinPoint joinPoint, MeHystrixCommand hystrixCommand) throws Exception {
        int timeout = hystrixCommand.timeout();

        Future<?> future = executorService.submit(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        });

        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            future.cancel(true);

            if (StringUtils.isBlank(hystrixCommand.fallback())) {
                throw e;
            }
            return invokeFallback(joinPoint, hystrixCommand.fallback());
        }
    }

    /**
     * 调用回退方法
     *
     * @param joinPoint:
     * @param fallback:
     * @author: rcy
     * @date: 2022-09-16
     * @return: java.lang.Object
     **/
    private Object invokeFallback(ProceedingJoinPoint joinPoint, String fallback) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();

        try {
            Method fallbackMethod = joinPoint.getTarget().getClass().getMethod(fallback, parameterTypes);
            fallbackMethod.setAccessible(true);
            return fallbackMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
