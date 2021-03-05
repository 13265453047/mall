package com.bruce;

import java.util.concurrent.*;

/**
 * @author rcy
 * @data 2021-03-05 11:19
 * @description TODO
 */
public class CallableDemo implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo demo = new CallableDemo();
        ExecutorService service = Executors.newCachedThreadPool();

        Future<String> submit = service.submit(demo);

        System.out.println(submit.get());

        service.shutdown();
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        int a = 1;
        int b = 3;
        int result = a + b;
        System.out.println(result);
        return String.valueOf(result);
    }
}
