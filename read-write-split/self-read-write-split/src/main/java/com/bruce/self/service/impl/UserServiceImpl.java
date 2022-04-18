package com.bruce.self.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bruce.self.annontions.DataSourceSwitcher;
import com.bruce.self.entity.User;
import com.bruce.self.enums.DataSourceEnum;
import com.bruce.self.mapper.UserMapper;
import com.bruce.self.service.IUserService;
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
    @DataSourceSwitcher(DataSourceEnum.MASTER)
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    @DataSourceSwitcher(DataSourceEnum.SLAVE_0)
    public List<User> list() {
        return userMapper.list();
    }

}
