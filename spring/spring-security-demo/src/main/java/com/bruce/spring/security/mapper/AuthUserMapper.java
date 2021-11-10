package com.bruce.spring.security.mapper;

import com.bruce.spring.security.define.authentication.UserPassword;
import org.springframework.stereotype.Component;

/**
 * @author rcy
 * @version 1.0.0
 * @className: AuthUserMapper
 * @date 2021/11/10 12:46
 */
// @Mapper
@Component
// public interface AuthUserMapper {
public class AuthUserMapper {

    public UserPassword selectByPrimaryKey(String username) {
        return new UserPassword();
    }

}
