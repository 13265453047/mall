package com.bruce.spring.security.define.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 登录认证的Provider，自定义实现了{@link AuthenticationProvider} <br>
 * Provider默认实现是 {@link DaoAuthenticationProvider} <br>
 * {@link BearerTokenAuthenticationFilter} 装填=> {@link Authentication}对象 <br>
 * {@link UsernamePasswordAuthenticationFilter} 调用=> {@link AuthenticationManager} => {@link AuthenticationProvider}验证 <br>
 *
 * @author rcy
 * @version 1.0.0
 * @className: UserAuthenticationProvider
 * @date 2021/11/10 12:34
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationProvider(UserDetailsServiceImpl userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // http请求的账户密码
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 数据库根据用户名查询
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        log.info("[http请求的账户密码]: {}/{}", username, password);
        log.info("[数据库查询的账户密码]：{} ", userDetails);

        if (userDetails == null) {
            throw new BadCredentialsException("用户名未找到");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        log.info("[设置authorities] : {}", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
    }

    /**
     * 是否支持处理当前Authentication对象类似
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
