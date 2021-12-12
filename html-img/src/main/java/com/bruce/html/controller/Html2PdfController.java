package com.bruce.html.controller;

import com.bruce.html.handler.AsyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author rcy
 * @data 2021-12-12 23:24
 * @description TODO
 */
@Slf4j
@RestController
public class Html2PdfController {

    @Autowired
    private AsyncHandler asyncHandler;

    @GetMapping
    public void html2Pdf() {
        Future<Boolean> future = null;

        String html = "";

        try {
            this.asyncHandler.htmlToPdf(html);
            // 等待子线程全部执行完毕
            future.get();
            System.out.println("生成pdf完成......");
        } catch (Exception e) {
            log.warn("create pdf error : " + e.getMessage());
        } finally {
            if (future != null) {
                // future.get();
            }
        }
    }

}
