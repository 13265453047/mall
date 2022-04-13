package com.bruce.sharding.controller;

import com.bruce.sharding.annontions.MasterDataSourceSwitcher;
import com.bruce.sharding.dao.entity.User;
import com.bruce.sharding.dto.UserDto;
import com.bruce.sharding.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rcy
 * @version 1.0.0
 * @className: UserController
 * @date 2022-04-12
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    // @MasterDataSourceSwitcher
    @GetMapping(value = "list")
    public Object list() {
        return userService.list();
    }

    @PostMapping(value = "add")
    public Object add(@RequestBody UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userService.addUser(user);
    }

}

