package com.bruce.spring.security.mapper;

import com.bruce.spring.security.model.SysRoleDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rcy
 * @version 1.0.0
 * @className: AuthRoleMapper
 * @date 2021/11/10 12:46
 */
// @Mapper
@Component
// public interface AuthRoleMapper {
public class AuthRoleMapper {

    public List<SysRoleDO> qryUserRoleByUsername(String username) {
        List<SysRoleDO> list = new ArrayList<>();
        return list;
    }

}
