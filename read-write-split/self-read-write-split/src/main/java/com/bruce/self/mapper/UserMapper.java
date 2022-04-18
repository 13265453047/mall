package com.bruce.self.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bruce.self.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author rcy
 * @version 1.0.0
 * @className: UserMapper
 * @date 2022-04-12
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    int addUser(@Param("user") User user);

    @Select("select * from user")
    List<User> list();

}
