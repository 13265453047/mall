package com.bruce.springbootdemo.controller;

import com.bruce.springbootdemo.annotation.LogAspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcy
 * @version 1.1.0
 * @className: AspectController
 * @date 2022-09-16
 */
@RestController
@LogAspect("cont")
public class AspectController {

    @LogAspect("hello")
    @GetMapping("hello")
    public String hello() {
        return "Hello world";
    }

}
