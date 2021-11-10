package com.bruce.spring.security.define.authentication;

import com.bruce.spring.security.mapper.AuthRoleMapper;
import com.bruce.spring.security.mapper.AuthUserMapper;
import com.bruce.spring.security.model.SysRoleDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录的service实现类
 * 框架的默认实现是{@link org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl}
 *
 * @author rcy
 * @version 1.0.0
 * @className: MyUserDetailsService
 * @date 2021/11/10 12:34
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserMapper authUserMapper;
    private final AuthRoleMapper authRoleMapper;

    @Autowired
    public UserDetailsServiceImpl(AuthUserMapper authUserMapper, AuthRoleMapper authRoleMapper) {
        this.authUserMapper = authUserMapper;
        this.authRoleMapper = authRoleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPassword userPassword = authUserMapper.selectByPrimaryKey(username);
        if (userPassword == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SysRoleDO> sysRoles = authRoleMapper.qryUserRoleByUsername(username);
        userPassword.setRoleList(sysRoles);
        log.info("[loadUserByUsername]: {}", userPassword);

//        User.withUsername(userPassword.getUsername())
//                .password(userPassword.getPassword())
//                .authorities(userPassword.getAuthorities());

        return userPassword;
    }

}
