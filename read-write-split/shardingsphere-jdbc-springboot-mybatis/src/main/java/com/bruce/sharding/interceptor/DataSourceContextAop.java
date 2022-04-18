package com.bruce.sharding.interceptor;

import com.bruce.sharding.annontions.MasterDataSourceSwitcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

    @Around("@annotation(com.bruce.sharding.annontions.MasterDataSourceSwitcher)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Method method = this.getMethod(pjp);
            boolean masterDataSource = method.isAnnotationPresent(MasterDataSourceSwitcher.class);
            if (masterDataSource && !HintManager.isMasterRouteOnly()) {
                // 强制读主库
                HintManager.clear();
                HintManager hintManager = HintManager.getInstance();
                hintManager.setMasterRouteOnly();
            }
            return pjp.proceed();
        } finally {
            HintManager.clear();
        }
    }

    private Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

}
