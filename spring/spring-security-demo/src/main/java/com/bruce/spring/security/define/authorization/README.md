# 工程简介

自定义权限验证的实现
SpringSecurity自定义权限验证时也需要实现三个接口：
AccessDecisionManager ： 自定义鉴权管理器，根据URL资源权限和用户角色权限进行鉴权
FilterInvocationSecurityMetadataSource：自定义权限数据源，提供所有URL资源与对应角色权限的映射集合
AbstractSecurityInterceptor ：资源访问过滤器，拦截访问请求，封装成安全对象FilterInvocation，调用前两个实例进行鉴权


配置类
WebSecurityConfigurerAdapter 继承后将上面自定义的一些配置添加到框架中

如果是OAuth2 认证还需要继承实现以下两个配置类：
ResourceServerConfigurerAdapter
AuthorizationServerConfigurerAdapter

# 延伸阅读
