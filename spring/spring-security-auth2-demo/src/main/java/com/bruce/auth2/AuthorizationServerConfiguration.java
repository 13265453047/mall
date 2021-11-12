package com.bruce.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * EnableAuthorizationServer
 * 授权服务器配置
 * <p>
 * EnableAuthorizationServer注解在
 * spring-security-oauth2-autoconfigure高版本被弃用
 *
 * @author rcy
 * @version 1.0.0
 * @className: AuthorizationServerConfiguration
 * @date 2021/11/11 11:53
 * <p>
 * Spring Security Oauth2 登录认证配置类
 * <p>
 * 顶级身份管理者: {@link AuthenticationManager}
 * 用来从请求中获取client_id,client_secret，组装成一个UsernamePasswordAuthenticationToken作为身份标识
 * --其实现类是 {@link ProviderManager}
 * ----内部维护的Provider数组中{@link DaoAuthenticationProvider}
 * ------内置了{@link UserDetailsService}接口实现，它是获取用户详细信息的最终接口
 * <p>
 * 经过前置校验和身份封装之后，便到达了{@link TokenEndpoint}
 * --其内部依赖{@link TokenGranter} 来颁发token, 包含5种授权模式
 * ----其抽象类中 {@link AbstractTokenGranter}
 * ------通过{@link AuthorizationServerTokenServices}来创建、刷新、获取token
 * --------其默认的实现类{@link DefaultTokenServices}，会调用tokenStore对创建的token和相关信息存储到对应的实现类中
 */

/**
 * 开启认证服务器功能
 * 主要作用：
 * 1）配置第三方客户端账号信息，
 * 2）然后授权给第三方客户端应用权限（通过不同的授权方式）
 * 3）可以进入资源服务器进行相应接口的调用，操作资源（增删改查）（拿到access_token发起调用）
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置AuthorizationServer安全认证的相关信息
     * 创建ClientCredentialsTokenEndpointFilter 客户端身份认证核心过滤器
     * 请求到达/oauth/token之前经过了ClientCredentialsTokenEndpointFilter这个过滤器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 配置OAuth2客户端数据
     * <p>
     * 方式：
     * 1）通过 ClientDetailsService 配置
     * 2）通过 内存（inMemory） 配置
     */
    /**
     * 配置被允许访问此认证服务器的客户端详情信息
     * 方式1：内存方式管理
     * 方式2：数据库管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用内存方式
        clients.inMemory()
                // 客户端id
                .withClient("wj-pc")
                // 客户端密码，要加密,不然一直要求登录
                .secret(passwordEncoder.encode("wj-secret"))
                // 资源id, 如商品资源
                .resourceIds("product-server")
                // 授权类型, 可同时支持多种授权类型
                .authorizedGrantTypes("authorization_code", "password", "implicit", "client_credentials", "refresh_token")
                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                .autoApprove(false)
                .redirectUris("http://www.baidu.com/");// 客户端回调地址，会将授权的临时 code 传给客户端

        clients.withClientDetails(inMemoryClientDetailsService());
    }

    /**
     * 可以使用内存形式也可以使用数据库等其他存储方式
     *
     * @return
     * @throws Exception
     */
    @Bean
    public ClientDetailsService inMemoryClientDetailsService() throws Exception {
        return new InMemoryClientDetailsServiceBuilder()
                // client oa application
                // 客户端id，允许访问此认证服务器的客户端id , 如：PC、APP、小程序各不同的的客户端id。
                .withClient("oa")
                // 客户端密码，要加密,不然一直要求登录
                .secret(passwordEncoder.encode("oa_secret"))
                // 资源id, 如商品资源
                .resourceIds("product-server")
                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                // 授权范围标识，如指定微服务名称，则只能访问指定的微服务。
                .scopes("all")
                // 授权类型, 可同时支持多种授权类型
                // 授权类型, 可同时支持多种授权类型：可配置："authorization_code", "password", "implicit","client_credentials","refresh_token"
                .authorizedGrantTypes("authorization_code", "refresh_token")
                // 客户端回调地址，当获取授权码后，认证服务器会重定向到这个URI，并且带着一个授权码code响应回来
                .redirectUris("http://localhost:8080/oa/login", "http://www.baidu.com")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                .autoApprove(true)
                .accessTokenValiditySeconds(7200)

                .and()

                // client crm application
                .withClient("crm")
                .secret(passwordEncoder.encode("crm_secret"))
                .scopes("all")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:8090/crm/login")
                .accessTokenValiditySeconds(7200)
                .autoApprove(true)

                .and()
                .build();
    }

    /**
     * Spring Security 对 OAuth2 默认提供了可直接访问端点，即URL：
     *
     * /oauth/authorize：申请授权码 code, 涉及的类AuthorizationEndpoint
     *
     * /oauth/token：获取令牌 token, 涉及的类TokenEndpoint/oauth/check_token：用于资源服务器请求端点来检查令牌是否有效, 涉及的类CheckTokenEndpoint
     *
     * /oauth/confirm_access：用户确认授权提交, 涉及的类WhitelabelApprovalEndpoint
     *
     * /oauth/error：授权服务错误信息, 涉及的类WhitelabelErrorEndpoint
     *
     * /oauth/token_key：提供公有密匙的端点，使用 JWT 令牌时会使用 , 涉及的类TokenKeyEndpoint
     *
     */


    /**
     * 测试
     *
     * 1）发送请求获取授权码 code
     * a:访问：localhost:8090/auth/oauth/authorize?client_id=wj-pc&response_type=code
     * b:这里的client_id是在AuthorizationServerConfig中配置的。
     * c:输入账号密码进行登陆，账号和密码：admin/1234 ,是在SpringSecurityConfig中配置的
     *
     * d:登陆成功后，选择Approve，点击Authorize，这里跳转到www.baidu.com ，并且后面携带了code，这里的code就是授权码，后面我们就可以通过授权码来获取令牌（access_token）
     * （http://www.baidu.com/?code=MbEzamc）
     *
     * 2）通过授权码获取令牌
     *
     * a:使用postman测试：http://localhost:8090/auth/oauth/token
     *
     * b:
     * 设置 Authorization 配置参数
     * 这里的
     * TYPE 选择 Basic Auth
     * username和password是在AuthorizationServerConfig中配置的（wj-pc，wj-secret）
     * 设置为post请求，并设置请求体
     *
     * c:
     * 这里的grant_type是authorization_code，code是上一步获取的 code
     * (http://localhost:8090/auth/oauth/token?grant_type=authorization_code&code=MbEzamc)
     * 发送请求后，获得了access_token
     * {
     *     "access_token":"fsdgddfgfgh56785868dfhf",
     *     "refresh_token":"45ds4f54sd4g5df4gcxgbfdhg",
     *     "token_type":"bearre",
     *     "expires_in":431986,
     *     "scope":"all"
     * }
     * 注意，code只能获取一次access_token，获取后就会失效，第二次获取就会失败
     *
     *
     */


    /**
     * 配置AuthorizationServerEndpointsConfigurer众多相关类，
     * 包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore());
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("123456");
        return jwtAccessTokenConverter;
    }
}
