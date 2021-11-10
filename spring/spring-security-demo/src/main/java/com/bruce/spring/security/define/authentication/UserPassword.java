package com.bruce.spring.security.define.authentication;

import com.bruce.spring.security.model.SysRoleDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 实现了 {@link UserDetails}接口
 * 用于构建存储在SecurityContextHolder的Authentication对象
 *
 * @author rcy
 * @version 1.0.0
 * @className: MyUserDetails
 * @date 2021/11/10 12:33
 */
@Slf4j
@Data
public class UserPassword implements UserDetails {

    private String username;

    private String password;

    private String name;

    /**
     * 拥有的角色
     */
    private List<SysRoleDO> roleList;

    /**
     * 拥有的权限
     */
    private List<String> permissions;


    // ... 其他字段省略

    /**
     * 装填用户的角色列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleList == null || roleList.size() < 1) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }
        log.info("[原始用户角色列表装填]: {}", roleList);
        StringBuilder roles = new StringBuilder();
        for (SysRoleDO role : roleList) {
            roles.append("ROLE_").append(role.getNo()).append(",");
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(roles.substring(0, roles.length() - 1));
        log.info("[遍历并返回用户的角色列表]: {}", authorityList);
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
