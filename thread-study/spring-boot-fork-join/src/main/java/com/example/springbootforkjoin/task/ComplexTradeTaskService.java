package com.example.springbootforkjoin.task;

import com.example.springbootforkjoin.model.Context;
import com.example.springbootforkjoin.service.SellerService;
import com.example.springbootforkjoin.service.ShopService;
import com.example.springbootforkjoin.task.AbstractLoadDataProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Service
public class ComplexTradeTaskService extends AbstractLoadDataProcessor implements ApplicationContextAware {

    ApplicationContext applicationContext;

    private List<AbstractLoadDataProcessor> taskDataProcessors=new ArrayList<>();

    @Override
    public void load(Context context) {
        taskDataProcessors.forEach(abstractLoadDataProcessor->{
            abstractLoadDataProcessor.setContext(this.context);
            abstractLoadDataProcessor.fork();//创建一个fork task
        });
    }

    @Override
    public Context getContext() {
        this.taskDataProcessors.forEach(ForkJoinTask::join);
        return super.getContext();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        taskDataProcessors.add(applicationContext.getBean(SellerService.class));
        taskDataProcessors.add(applicationContext.getBean(ShopService.class));
    }
}