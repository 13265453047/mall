package com.bruce.springbootdemo.cycledi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class StudentB {

    @Autowired
    private StudentC studentC;

//    public StudentB(StudentC studentC) {
//        this.studentC = studentC;
//    }


//    public void setStudentC(StudentC studentC) {
//        this.studentC = studentC;
//    }
}
