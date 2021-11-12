package com.bruce.spring.security.define.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 资源访问过滤器 <br>
 * 重写了{@link AbstractSecurityInterceptor} 接口 <br>
 * 默认的过滤器实现是{@link FilterSecurityInterceptor}
 *
 * @author rcy
 * @version 1.0.0
 * @className: AccessSecurityInterceptor
 * @date 2021/11/10 13:52
 */
@Slf4j
@Component
public class AuthorizeSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Resource
    private AuthorizeSecurityMetadataSource authorizeSecurityMetadataSource;
    @Resource
    private AuthorizeAccessDecisionManager authorizeAccessDecisionManager;

    /**
     * 初始化时将自定义的DecisionManager，注入到父类AbstractSecurityInterceptor中
     */
    @PostConstruct
    public void init() {
        super.setAccessDecisionManager(authorizeAccessDecisionManager);
    }

    /**
     * 重写父类AbstractSecurityInterceptor，获取到自定义MetadataSource的方法
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.authorizeSecurityMetadataSource;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[自定义过滤器]: {}", " AuthorizeSecurityInterceptor.doFilter()");
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // 调用父类的 beforeInvocation ==> accessDecisionManager.decide(..)
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            // 调用父类的 afterInvocation ==> afterInvocationManager.decide(..)
            super.afterInvocation(token, null);
        }
    }

    /**
     * 向父类提供要处理的安全对象类型，因为父类被调用的方法参数类型大多是Object，框架需要保证传递进去的安全对象类型相同
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("----------AuthorizeSecurityInterceptor.init()");
    }

    @Override
    public void destroy() {
        System.out.println("----------AuthorizeSecurityInterceptor.destroy()");
    }

}
