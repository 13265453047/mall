package com.bruce.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    int deleteById(@Param("id") Long id);

}
