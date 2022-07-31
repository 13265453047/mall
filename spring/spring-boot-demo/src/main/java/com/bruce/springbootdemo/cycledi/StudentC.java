package com.bruce.springbootdemo.cycledi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class StudentC {

     @Autowired
    private StudentA studentA;

//    public StudentC(StudentA studentA) {
//        this.studentA = studentA;
//    }


//    public void setStudentA(StudentA studentA) {
//        this.studentA = studentA;
//    }
}
