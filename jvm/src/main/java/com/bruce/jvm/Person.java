package com.bruce.jvm;

import java.io.Serializable;

/**
 * @author rcy
 * @data 2022-02-08 21:36
 * @description TODO
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 1480856157229481058L;

    private int age;

    private String name;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
