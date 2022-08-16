package com.bruce.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class B2bCompanyMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 状态  0：禁用，1：正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}
