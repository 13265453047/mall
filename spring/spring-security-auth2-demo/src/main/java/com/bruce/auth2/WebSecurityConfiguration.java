package com.bruce.auth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Spring Security 配置
 * 系统账户信息
 *
 * @author rcy
 * @version 1.0.0
 * @className: WebSecurityConfiguration
 * @date 2021/11/11 13:30
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable().cors();
    }

    /**
     * 配置系统的人员账号信息（认证数据）
     * 可以通过内存、数据库配置人员账号信息：
     * a:账号名
     * b:密码
     * c:角色
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());

        // 内存方式存储用户信息,这里为了方便就不从数据库中查询了
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder().encode("1234"))
                .authorities("product");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        // 这里可以读取内存也可以读取数据库 或者 其他存储的数据
        Collection<UserDetails> users = buildUsers();
        return new InMemoryUserDetailsManager(users);
    }

    /**
     * 可以登录系统的账号信息
     *
     * @return
     */
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
     * 引入PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码模式必须设置对应的AuthenticationManager，所以这里必须暴露出来，否则系统找不到
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
