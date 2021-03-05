package com.bruce.chapt01;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author rcy
 * @data 2021-03-05 11:37
 * @description TODO
 */
public class SaveProcessor extends Thread implements RequestProcessor {

    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    @Override
    public void processor(Request request) {
        queue.add(request);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    @Override
    public void run() {
        for (; ; ) {
            Request request = queue.take();
            System.out.println("save -> " + request);
        }
    }

}
