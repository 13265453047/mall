package com.bruce.chapt01;

/**
 * @author rcy
 * @data 2021-03-05 11:30
 * @description 通过阻塞队列以及多线程的方式，实现队请求的异步化处理，提升处理性能
 * 一个简单责任链模式
 */
public class Demo {

    private PrintProcessor printProcessor;

    public Demo() {
        SaveProcessor saveProcessor = new SaveProcessor();
        this.printProcessor = new PrintProcessor(saveProcessor);
        saveProcessor.start();
        printProcessor.start();
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        Request request = new Request("Mic");
        demo.process(request);
    }

    private void process(Request request) {
        printProcessor.processor(request);
    }
}
