package com.bruce.self.interceptor;

import com.bruce.self.annontions.DataSourceSwitcher;
import com.bruce.self.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceContextAop
 * @date 2022-04-12
 */
@Slf4j
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {

    @Around("@annotation(com.bruce.self.annontions.DataSourceSwitcher)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = false;
        try {
            Method method = this.getMethod(pjp);
            DataSourceSwitcher dataSourceSwitcher = method.getAnnotation(DataSourceSwitcher.class);
            if (Objects.nonNull(dataSourceSwitcher)) {
                clear = dataSourceSwitcher.clear();
                DataSourceContextHolder.set(dataSourceSwitcher.value().getDataSourceName());
            }

            log.info("数据源切换至：{}", dataSourceSwitcher.value().getDataSourceName());
            return pjp.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

    private Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

//    @Around("@annotation(com.bruce.self.annontions.DataSourceSwitcher)")
//    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
//        boolean clear = false;
//        try {
//            Method method = this.getMethod(pjp);
//            DataSourceSwitcher dataSourceSwitcher = method.getAnnotation(DataSourceSwitcher.class);
//            clear = dataSourceSwitcher.clear();
//            DataSourceContextHolder.set(dataSourceSwitcher.value().getDataSourceName());
//            log.info("数据源切换至：{}", dataSourceSwitcher.value().getDataSourceName());
//            return pjp.proceed();
//        } finally {
//            if (clear) {
//                DataSourceContextHolder.clear();
//            }
//
//        }
//    }
//
//    private Method getMethod(JoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        return signature.getMethod();
//    }

}
