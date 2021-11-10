package com.bruce.spring.security.define.authorization;

import com.bruce.spring.security.mapper.AuthPermissionMapper;
import com.bruce.spring.security.mapper.AuthRoleMapper;
import com.bruce.spring.security.model.SysPermissionDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限资源映射的数据源 <br>
 * 这里重写并实现了基于数据库的权限数据源 <br>
 * 实现了 {@link FilterInvocationSecurityMetadataSource}接口 <br>
 * 框架的默认实现是 {@link DefaultFilterInvocationSecurityMetadataSource} <br>
 *
 * @author rcy
 * @version 1.0.0
 * @className: AccessSecurityMetadataSource
 * @date 2021/11/10 13:51
 */
@Slf4j
@Service
public class AuthorizeSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    // key 是url+method ， value 是对应url资源的角色列表
    private static Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap;

    // 从数据库取出权限数据，代码略
    private AuthRoleMapper authRoleMapper;
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    AuthorizeSecurityMetadataSource(AuthRoleMapper authRoleMapper, AuthPermissionMapper authPermissionMapper) {
        this.authPermissionMapper = authPermissionMapper;
        this.authRoleMapper = authRoleMapper;
    }

    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();

        // 需要鉴权的url资源，@needAuth标志
        List<SysPermissionDO> permissionList = authPermissionMapper.queryRolePermission();
        for (SysPermissionDO permission : permissionList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            String[] roles = permission.getRoleList().split(",");
            log.info("{} - {}", url, method);
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);

            /*Collection<ConfigAttribute> attributes = new ArrayList<>();
            for (String role : roles) {
                attributes.add(new SecurityConfig(role));
            }*/
            Collection<ConfigAttribute> attributes = SecurityConfig.createList(roles);


            // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
            attributes.add(new SecurityConfig("@needAuth"));
            permissionMap.put(requestMatcher, attributes);
        }

        // 公共的url资源 & 系统接口的url资源，value为null
        List<SysPermissionDO> publicList = authPermissionMapper.queryPublicPermission();
        for (SysPermissionDO permission : publicList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, "*".equals(method) ? null : method);
            // value为空时不做鉴权，相当于所有人都可以访问该资源URL
            permissionMap.put(requestMatcher, null);
        }

        // 多余的url资源， @noAuth，所有人都无法访问
        Collection<ConfigAttribute> attributes = new ArrayList<>();
        attributes.add(new SecurityConfig("@noAuth"));
        permissionMap.put(new AntPathRequestMatcher("/**", null), attributes);

        log.info("[全局权限映射集合初始化]: {}", permissionMap.toString());
    }

    /**
     * 鉴权时会被AbstractSecurityInterceptor.beforeInvocation()调用，根据URL找到对应需要的权限
     *
     * @param object 安全对象类型 FilterInvocation.class
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("[资源被访问：根据URL找到权限配置]: {}\n {}", object, permissionMap);

        if (permissionMap == null) {
            loadResourceDefine();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                log.info("[找到的Key]: {}", entry.getKey());
                log.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，返回所有的 Collection<ConfigAttribute> ，以筛选出不符合要求的attribute
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，验证指定的安全对象类型是否被MetadataSource支持
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
