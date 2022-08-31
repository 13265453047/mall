package com.bruce.ribbon.qualifier;

/**
 * @author rcy
 * @version 1.1.0
 * @className: TestClass
 * @date 2022-08-31
 */
public class TestClass {

   private String className;

    public TestClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "className='" + className + '\'' +
                '}';
    }
}
