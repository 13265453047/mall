//package com.bruce.hystrix.aspect;
//
//import com.netflix.hystrix.contrib.javanica.command.AbstractHystrixCommand;
//import com.netflix.hystrix.contrib.javanica.command.HystrixCommandBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.concurrent.*;
//
///**
// * 自定义Hystrix拦截切面
// *
// * @author rcy
// * @version 1.1.0
// * @className: MyHystrixCommand
// * @date 2022-09-14
// */
//@Slf4j
//public class MyHystrixCommandAspect extends AbstractHystrixCommand<String> {
//
//    private RestTemplate restTemplate;
//
////    protected MyHystrixCommand(RestTemplate restTemplate) {
////        this.restTemplate = restTemplate;
////    }
//
//    protected MyHystrixCommandAspect(HystrixCommandBuilder builder) {
//        super(builder);
//    }
//
//    @Override
//    protected String run() throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        Future<?> future = executorService.submit(() -> {
//            // 调用执行方法 TODO
//
//        });
//        try {
//            Object result = future.get(1000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            log.error("请求超时");
//            // 调用失败回退方法 TODO
//
//        }
//        return null;
//    }
//
//}
