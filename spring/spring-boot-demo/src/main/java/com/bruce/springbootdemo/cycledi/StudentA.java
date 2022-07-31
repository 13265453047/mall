package com.bruce.springbootdemo.cycledi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// setter方式原型，prototype
//@Scope("prototype")
public class StudentA {

    // 注入注入
    @Autowired
    private StudentB studentB;
//    @Autowired
//    private StudentA studentA;


    // 构造器注入（无法处理循环依赖），除设置对象为Prototype作用域
//    public StudentA(StudentB studentB) {
//        this.studentB = studentB;
//    }


    // set注入
//    public void setStudentB(StudentB studentB) {
//        this.studentB = studentB;
//    }
}
