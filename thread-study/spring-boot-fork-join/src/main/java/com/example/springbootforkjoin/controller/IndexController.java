package com.example.springbootforkjoin.controller;

import com.example.springbootforkjoin.model.Context;
import com.example.springbootforkjoin.task.ItemTaskForkJoinDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ForkJoinPool;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@RestController
public class IndexController {

    @Autowired
    ItemTaskForkJoinDataProcessor itemTaskForkJoinDataProcessor;

    @GetMapping("/say")
    public Context index(){
        Context context=new Context();
        itemTaskForkJoinDataProcessor.setContext(context);
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        forkJoinPool.submit(itemTaskForkJoinDataProcessor);
        return itemTaskForkJoinDataProcessor.getContext();
    }
}

