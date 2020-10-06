package com.bruce;

import com.bruce.config.SpringConfig;
import com.bruce.domain.RpcProxyClient;
import com.bruce.service.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *@description TODO
 *@data 2020/10/6 20:39
 *@author Bruce.Ren
 *@version 1.0
**/
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient=context.getBean(RpcProxyClient.class);

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"v1.0");
        for(int i=0;i<100;i++) {
            Thread.sleep(2000);
            System.out.println(iHelloService.sayHello(1.0));
        }
    }

}
