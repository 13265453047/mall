package com.bruce.spring.security.config;

import com.bruce.spring.security.define.authentication.UserAuthenticationProvider;
import com.bruce.spring.security.define.authorization.AuthorizeSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author rcy
 * @version 1.0.0
 * @className: WebSecurityConfig
 * @date 2021/11/10 9:28
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证提供者
     */
    @Resource
    private UserAuthenticationProvider authenticationProvider;
    /**
     * 授权拦截器
     */
    @Resource
    private AuthorizeSecurityInterceptor authorizeSecurityInterceptor;

    /**
     * 启动时，不拦截静态文件
     * Creating filter chain: Ant [pattern='/js/**'], []
     * Creating filter chain: Ant [pattern='/css/**'], []
     * Creating filter chain: Ant [pattern='/images/**'], []
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    /**
     * 配置的请求走这过滤器链，此处配置是第一步认证，还需要第二步鉴权
     * 此处 permitAll() 相当于匿名免认证，框架会默认他是一个匿名角色
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf().disable()：spring security为防止CSRF（Cross-site request forgery跨站请求伪造）的发生，限制了除了get以外的大多数方 法。这个就是来设置屏蔽CSRF控制。
                .csrf().disable() // 关闭默认的csrf认证

                // 第一部分（授权）：
                .authorizeRequests() // 允许基于使用HttpServletRequest限制访问
                .antMatchers(
                        "/",
                        "/swagger-ui.html",
                        "/v2/**",
                        "/webjars/**",
                        "/swagger-resources/**"
                ).permitAll() // 访问首页不需要认证
                .antMatchers("/r/r1").hasAuthority("1001") // 配置这个资源的路径必须有后面的权限标志才能访问否则403
                .anyRequest().authenticated()  // 其他页面需要认证

                .and()

                // 第二部分（认证）：
                .formLogin() // 支持表单登陆
                .loginPage("/login-view") // 指定我们自己的登录页
                .loginProcessingUrl("/login") // 指定登录处理的URL，也就是用户名、密码表单提交的目的路径
                .successForwardUrl("/login-success") // 自定义登录成功的页面地址
                .permitAll() // 允许所有用户访问我们的登录页

                .and()

                // 第三部分（退出）：
                .logout() // 提供系统退出支持，使用 WebSecurityConfigurerAdapter 会自动被应用
                .logoutUrl("/logout") // 指定退出路径，设置触发退出操作的 URL ( 默认是 /logout )。
                .logoutSuccessUrl("/login-view?logout") // 退出之后跳转的 URL 。默认是 /login?logout 。

                // .logoutSuccessHandler(logoutSuccessHandler) // 定制的 LogoutSuccessHandler ，用于实现用户退出成功时的处理。如果指定了这个选项那么logoutSuccessUrl() 的设置会被忽略。
                // .addLogoutHandler(logoutHandler)// 添加一个 LogoutHandler ，用于实现用户退出时的清理工作 . 默认 SecurityContextLogoutHandler 会被添加 为最后一个 LogoutHandler
                .invalidateHttpSession(true) // 指定是否在退出时让 HttpSession 无效。 默认设置为 true 。

                .permitAll()    // 退出不需要权限

                .and()
                .httpBasic()
                .and()

                .addFilterAt(this.authorizeSecurityInterceptor, FilterSecurityInterceptor.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);  // 自定义provider
        auth.eraseCredentials(false);           // 不删除凭据，以便记住用户

        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        Collection<UserDetails> users = buildUsers();
        return new InMemoryUserDetailsManager(users);
    }

    private Collection<UserDetails> buildUsers() {
        String password = passwordEncoder().encode("123456");
        List<UserDetails> users = new ArrayList<>();
        UserDetails user_admin = User.withUsername("admin").password(password).authorities("ADMIN", "USER").build();
        UserDetails user_user1 = User.withUsername("user 1").password(password).authorities("USER").build();
        users.add(user_admin);
        users.add(user_user1);
        return users;
    }

    /**
     * 手动在拦截器中配置注册一个单例的bean对象，避免每次都重新生成
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
