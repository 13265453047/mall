package com.bruce.sharding.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import groovy.transform.EqualsAndHashCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author rcy
 * @version 1.0.0
 * @className: User
 * @date 2022-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String userName;

    private Integer age;

    private String phone;

    private Date createTime;

    private Date updateTime;


}
