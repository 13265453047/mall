package com.bruce.chapt01;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author rcy
 * @data 2021-03-05 11:33
 * @description TODO
 */
public class PrintProcessor extends Thread implements RequestProcessor {

    private final RequestProcessor next;
    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public PrintProcessor(RequestProcessor next) {
        this.next = next;
    }

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
            System.out.println("print -> " + request);
            next.processor(request);
        }
    }
}
