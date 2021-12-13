package com.bruce.html.wkhtml;

/**
 * @author rcy
 * @version 1.0.0
 * @className: HtmlToPdfInterceptor
 * @date 2021-12-13 11:13
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * wkhtmltopdf.exe执行信息拦截
 *
 */
public class HtmlToPdfInterceptor extends Thread {
    private InputStream is;

    public HtmlToPdfInterceptor(InputStream is){
        this.is = is;
    }

    public void run(){
        try{
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line.toString()); //输出内容
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
