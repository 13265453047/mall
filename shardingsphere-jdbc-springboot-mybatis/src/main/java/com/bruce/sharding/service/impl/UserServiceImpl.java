package com.bruce.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bruce.sharding.dao.entity.User;
import com.bruce.sharding.dao.mapper.UserMapper;
import com.bruce.sharding.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rcy
 * @version 1.0.0
 * @className: UserServiceImpl
 * @date 2022-04-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
