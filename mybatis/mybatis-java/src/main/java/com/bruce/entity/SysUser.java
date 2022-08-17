package com.bruce.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购管理系统用户表
 *
 * @ClassName: SysUser
 * @Author rcy
 * @Date 2021/8/17 11:28
 * @Version 1.0.0
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 真实名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 头像地址
     */
    private String headUrl;
    /**
     * 状态  0：禁用，1：正常
     */
    private Integer status;
    /**
     * 成员类型  0：平台管理员，1：销售代表，2：销售支持
     */
    private Integer type;
    /**
     * 部门编码
     */
    private String deptCode;
    /**
     * 本次登录时间
     */
    private Date loginDate;
    /**
     * 上次登录时间
     */
    private Date lastLoginDate;
    /**
     * 本次登录IP
     */
    private String loginIp;
    /**
     * 上次登录IP
     */
    private String lastLoginIp;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作人用户名
     */
    private String operator;

}
