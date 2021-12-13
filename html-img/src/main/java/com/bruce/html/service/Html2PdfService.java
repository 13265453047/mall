package com.bruce.html.service;

import com.bruce.html.HtmlStr;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 */
@Slf4j
@Service
public class Html2PdfService {

    /**
     * 将html文件转换成pdf
     *
     * @param htmlStr  ： html字符串
     * @param fileName ： 保存的文件名
     * @throws Exception
     */
    public void htmlToPdf(String htmlStr, String fileName) throws Exception {
        log.info("thread begin {}", Thread.currentThread().getName());
        try (OutputStream os = new FileOutputStream(fileName)) {
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // DOCTYPE 必需写否则类似于 这样的字符解析会出现错误
            /*String html5 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">" +
                    "<head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
                    "</head>" +
                    "<body>" +
                    htmlStr +
                    "</body></html>";*/
            renderer.setDocumentFromString(htmlStr);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            log.info("pdf create success : {}", fileName);
        }
    }

}
