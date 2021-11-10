package com.bruce.spring.security.define.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * AccessDecisionManager 授权决策管理器
 * <p>
 * 主体的权限保存到 Authentication 里
 * Authentication 对象保存在一个 GrantedAuthority 的对象数组中。
 * AccessDecisionManager 遍历数组进行授权判断。
 * AccessDecisionManager 被 AbstractSecurityInterceptor 调用，作最终访问控制的决定
 * <p>
 * {@link AccessDecisionManager} 鉴权决策管理器 <br>
 * 被鉴权决策管理器 被{@link AbstractSecurityInterceptor} 调用进行鉴权 <br>
 * 框架默认实现是 {@link UnanimousBased}
 *
 * @author rcy
 * @version 1.0.0
 * @className: AccessDecisionManagerImpl
 * @date 2021/11/10 13:46
 */
@Slf4j
@Service
public class AuthorizeAccessDecisionManager implements AccessDecisionManager {
    /**
     * 权限鉴定
     *
     * @param authentication   from SecurityContextHolder.getContext() =》 userDetails.getAuthorities()
     * @param object           是一个安全对象类型，FilterInvocation.class
     * @param configAttributes from MetaDataSource.getAttributes()，已经被框架做了非空判断
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("[资源权限]: {}", configAttributes);
        log.info("[用户权限]: {}", authentication.getAuthorities());

        for (ConfigAttribute resourceAttr : configAttributes) {
            // 资源的权限
            String resourceRole = "ROLE_" + resourceAttr.getAttribute();

            // 用户的权限
            for (GrantedAuthority userAuth : authentication.getAuthorities()) {
                log.info("[资源角色==用户角色] ？ {} == {}", resourceRole.trim(), userAuth.getAuthority().trim());
                if (resourceRole.trim().equals(userAuth.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    /**
     * 被AbstractSecurityInterceptor调用，遍历ConfigAttribute集合，筛选出不支持的attribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 被AbstractSecurityInterceptor调用，验证AccessDecisionManager是否支持这个安全对象的类型。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
