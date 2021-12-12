package com.bruce.html.handler;

import com.bruce.html.service.Html2PdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Async异步方法
 *
 * @author feng.guo
 * @since 2019/12/20
 */
@Async
@Component
@Slf4j
public class AsyncHandler {

    @Autowired
    private Html2PdfService html2PdfService;

    public Future<Boolean> htmlToPdf(String html) throws Exception {
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".pdf";
        try {
            html2PdfService.htmlToPdf(html, fileName);
            return new AsyncResult<>(true);
        } catch (Exception e) {
            log.warn("thread error : {}", e.getMessage());
            throw e;
        }
    }

}
