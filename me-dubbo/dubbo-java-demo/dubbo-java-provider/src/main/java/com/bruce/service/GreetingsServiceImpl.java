package com.bruce.service;

import com.bruce.api.GreetingsService;

/**
 * @author rcy
 * @version 1.1.0
 * @className: GreetingsServiceImpl
 * @date 2022-08-26
 */
public class GreetingsServiceImpl implements GreetingsService {

    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }

}
