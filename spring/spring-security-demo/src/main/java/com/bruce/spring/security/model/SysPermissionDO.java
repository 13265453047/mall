package com.bruce.spring.security.model;

import lombok.Data;

/**
 * @author rcy
 * @version 1.0.0
 * @className: SysPermissionDO
 * @date 2021/11/10 13:56
 */
@Data
public class SysPermissionDO {

    private String url;
    private String method;
    private String roleList;

}
