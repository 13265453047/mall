package com.bruce.html.controller;

import com.bruce.html.handler.AsyncHandler;
import com.bruce.html.pdf2img.icepdf.ImageUtils;
import com.bruce.html.utils.FreemarkerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * 将图片写出
     *
     * @param response:
     * @author: rcy
     * @date: 2021-12-14 09:12:30
     * @return: void
     **/
    @GetMapping
    public void downloadImg(HttpServletResponse response) throws Exception {
        BufferedImage img = null;
        response.setContentType("image/jpeg");
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        ImageIO.write(img, "png", response.getOutputStream());
    }


    @GetMapping
    public void html2Pdf() throws Exception {
        Future<Boolean> future = null;
        String htmlFile = FreemarkerUtils.getTemplate("sell_ht.ftl", generateMapData());
        String deskPath = "F:\\wkhtmltopdf\\test1.pdf";
        String imgPath = "F:\\wkhtmltopdf\\";
        try {
            // this.asyncHandler.htmlToPdf(HtmlStr.getHtmlStr());
            // this.asyncHandler.htmlToPdf(html);
            future = this.asyncHandler.wkHtmlToPdf(htmlFile, deskPath);
            // 等待子线程全部执行完毕
            if (future.get()) {
                ImageUtils.tranfer(deskPath, imgPath, 1);
                //PDFToImgUtil.pdfToImage(deskPath, imgPath, 150);
                System.out.println("生成pdf完成......");
            }
        } catch (Exception e) {
            log.warn("create pdf error : " + e.getMessage());
        } finally {
            if (future != null) {
                // future.get();
                // 删除生成的临时html文件
                // TODO
            }
        }
    }

    public static Map<String, Object> generateMapData() {
        Map<String, Object> map = new HashMap<>();
        // 收货地址
        map.put("recive_addr", "收货地址");

        // 签订日期 ${qd_year}年 ${qd_month}月 ${qd_date}日
        map.put("qd_year", "2021");
        map.put("qd_month", "11");
        map.put("qd_date", "12");

        // 销售订单号： ${order_no}
        map.put("order_no", "555566655656555566655656");
        // 履行期限${lx_s_year}年${lx_s_month}月${lx_s_date}日至${lx_e_year}年${lx_e_month}月 ${lx_e_date}日
        map.put("lx_s_year", "2021");
        map.put("lx_s_month", "11");
        map.put("lx_s_date", "12");
        map.put("lx_e_year", "2021");
        map.put("lx_e_month", "12");
        map.put("lx_e_date", "115");

        // 结算方式 ${pay_pattern}
        map.put("settle_pattern", "线下账期");
        // 付款方式:电汇  银行承兑汇票
        map.put("pay_pattern", "银行承兑汇票");


        // ${list.name}	${list.spec}	${list.unit}	${list.price}	${list.count}	${list.amount}	${list.remark}
        map.put("listData", getListData());
        // 合 计				${total_count}	${total_amount}
        map.put("total_count", "125");
        map.put("total_amount", "558.35");

        // 甲方代表：${company_contact}
        map.put("company_contact", "张三");
        // 甲方公司（盖章）：${company_name}
        map.put("company_name", "上海外滩有限公司上海外滩有限公司");
        // 甲方地址：${company_addr}
        map.put("company_addr", "中国上海徐汇区外滩街道中国上海徐汇区外滩街道中国上海徐汇区外滩街道");
        // 开户银行: ${company_bank}
        map.put("company_bank", "中国银行");
        // 银行帐号： ${company_bank_no}
        map.put("company_bank_no", "4000029309200574529");
        // 电话： ${company_tel}
        map.put("company_tel", "0755-86568427");

        return map;
    }


    // ${list.name}	${list.spec}	${list.unit}	${list.price}	${list.count}	${list.amount}	${list.remark}
    public static List<Map<String, String>> getListData() {
        List<Map<String, String>> listData = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "海王健康蛋白粉海王健康蛋白粉" + i);
            map.put("spec", "规格");
            map.put("unit", "盒");
            map.put("price", "10.25");
            map.put("count", "21");
            map.put("amount", "56.35");
            map.put("remark", "备注babbab备注babbab备注babbab");

            listData.add(map);
        }
        return listData;
    }

}
