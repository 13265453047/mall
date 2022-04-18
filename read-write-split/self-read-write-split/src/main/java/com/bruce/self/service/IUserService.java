package com.bruce.self.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bruce.self.entity.User;

import java.util.List;

/**
 * @author rcy
 * @version 1.0.0
 * @className: IUserService
 * @date 2022-04-12
 */
public interface IUserService extends IService<User> {

    int addUser(User user);

    List<User> list();
}
