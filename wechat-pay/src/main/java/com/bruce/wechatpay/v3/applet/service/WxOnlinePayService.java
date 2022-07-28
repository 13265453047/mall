package com.bruce.wechatpay.v3.applet.service;

import com.bruce.wechatpay.v3.applet.util.WeChatPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 *
 * @author rcy
 * @version 1.0.0
 * @className: WxOnlinePayService
 * @date 2021/9/21 15:59
 */
@Slf4j
@Service
public class WxOnlinePayService {

    /**
     * 微信小程序支付回调
     *
     * @param request
     * @param response
     */
    @Transactional(rollbackFor = Exception.class)
    public Map callback(HttpServletRequest request, HttpServletResponse response) {
        // 应答时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        // 应答随机串
        String nonce = request.getHeader("Wechatpay-Nonce");
        // 微信支付证书序列号
        String serialNumber = request.getHeader("Wechatpay-Serial");
        // 应答签名
        String signature = request.getHeader("Wechatpay-Signature");

        System.out.println("Wechatpay-Timestamp:" + timestamp);
        System.out.println("Wechatpay-Nonce:" + nonce);
        System.out.println("Wechatpay-Serial:" + serialNumber);
        System.out.println("Wechatpay-Signature:" + signature);

        Map<String, String> result = new HashMap<>();
        result.put("code", "FAIL");

        // 回调数据
        String callBackJsonStr = getCallBackJsonStr(request);

        // 验签
        /*
            应答时间戳\n
            应答随机串\n
            应答报文主体\n
         */
        StringBuilder builder = new StringBuilder();
        builder.append(timestamp).append("\n");
        builder.append(nonce).append("\n");
        builder.append(callBackJsonStr).append("\n");

        if (!WeChatPayUtil.signVerifier(serialNumber, builder.toString(), signature)) {
            result.put("message", "sing error");
            return result;
        }

        // 解密密文
        String tradeOrderStr = WeChatPayUtil.decryptOrder(callBackJsonStr);


        // 验证订单金额
        // 处理订单 TODO

        return result;
    }

    /**
     * 获取回调数据
     *
     * @param request
     * @return
     */
    private String getCallBackJsonStr(HttpServletRequest request) {
        String requestBody = null;
        try {
            BufferedReader reader = request.getReader();
            String str = null;
            StringBuilder builder = new StringBuilder();

            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }

            requestBody = builder.toString();
            System.out.println(requestBody);
//            {
//                "id": "EV-2018022511223320873",
//                    "create_time": "2015-05-20T13:29:35+08:00",
//                    "resource_type": "encrypt-resource",
//                    "event_type": "TRANSACTION.SUCCESS",
//                    "summary": "支付成功",
//                    "resource": {
//                        "original_type": "transaction",
//                            "algorithm": "AEAD_AES_256_GCM",
//                            "ciphertext": "",
//                            "associated_data": "",
//                            "nonce": ""
//                    }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBody;
    }

}