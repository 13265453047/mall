package com.bruce;

import com.bruce.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 服务启动类
 * @data 2020/10/6 19:32
 **/
public class Bootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(SpringConfig.class);
        context.start();
    }

}
