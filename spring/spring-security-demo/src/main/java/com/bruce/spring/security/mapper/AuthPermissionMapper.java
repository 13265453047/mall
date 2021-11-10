package com.bruce.spring.security.mapper;

import com.bruce.spring.security.model.SysPermissionDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rcy
 * @version 1.0.0
 * @className: AuthPermissionMapper
 * @date 2021/11/10 13:56
 */
// @Mapper
@Component
// public interface AuthPermissionMapper {
public class AuthPermissionMapper {
    public List<SysPermissionDO> queryRolePermission() {
        List<SysPermissionDO> list = new ArrayList<>();
        return list;
    }

    public List<SysPermissionDO> queryPublicPermission() {
        List<SysPermissionDO> list = new ArrayList<>();
        return list;
    }

}
