package com.bruce.wechatpay.v3.applet.util;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @version 1.1.0
 * @className: WeChatPayUtil
 * @date 2022-07-27
 */
@Slf4j
public class WeChatPayUtil {

    /**
     * 下单
     */
    public static Map<String, String> createOrder() throws Exception {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(WeChatPayConstants.PRIVATE_KEY);

        // 微信支付证书会定期更新
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(WeChatPayConstants.MCH_ID, new WechatPay2Credentials(WeChatPayConstants.MCH_ID,
                        new PrivateKeySigner(WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                WeChatPayConstants.API_V3_KEY.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(WeChatPayConstants.MCH_ID);

        // 构造httpclient
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(WeChatPayConstants.MCH_ID, WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", WeChatPayConstants.MCH_ID)
                .put("appid", WeChatPayConstants.APP_ID)
                .put("description", "Image形象店-深圳腾大-QQ公仔")
                .put("notify_url", WeChatPayConstants.NOTIFY_URL)
                .put("out_trade_no", "1217752501201407033233368018");
        rootNode.putObject("amount")
                .put("total", 1);
        rootNode.putObject("payer")
                .put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");

        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

        String prepayId = "";

        //完成签名并执行请求
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String respBody = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                System.out.println("success,return body = " + respBody);
                ObjectMapper respObjectMapper = new ObjectMapper();
                JsonNode node = respObjectMapper.readTree(respBody);
                prepayId = node.get("prepay_id").textValue();
            } else if (statusCode == 204) {
                System.out.println("success");
            } else {
                System.out.println("failed,resp code = " + statusCode + ",return body = " + respBody);
                throw new IOException("request failed");
            }
        }

        String timestamp = System.currentTimeMillis() / 1000 + "";
        String nonce = RandomUtil.randomString(32);

        StringBuilder builder = new StringBuilder();
        builder.append(WeChatPayConstants.APP_ID).append("\n");
        builder.append(timestamp).append("\n");
        builder.append(nonce).append("\n");
        builder.append("prepay_id=").append(prepayId).append("\n");
        builder.append("\n");

        String sign = sign(builder.toString());

        Map<String, String> result = new HashMap<>();
        result.put("timeStamp", timestamp);
        result.put("nonceStr", nonce);
        result.put("package", "prepay_id=" + prepayId);
        result.put("signType", "RSA");
        result.put("paySign", sign);
        return result;
    }

    /**
     * 签名
     */
    public static String sign(String data) throws Exception {
        return RsaKit.encryptByPrivateKey(data, WeChatPayConstants.PRIVATE_KEY);
    }

    /**
     * 回调验签
     *
     * @param serialNumber :  微信支付证书序列号
     * @param message      :  签名串内容
     * @param signature    :  应答签名串
     * @author: rcy
     * @date: 2022-07-28
     * @return: boolean
     **/
    public static boolean signVerifier(String serialNumber, String message, String signature) {
        Verifier verifier;

        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(WeChatPayConstants.PRIVATE_KEY);

            // 微信支付证书会定期更新
            // 获取证书管理器实例
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(WeChatPayConstants.MCH_ID, new WechatPay2Credentials(WeChatPayConstants.MCH_ID,
                            new PrivateKeySigner(WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                    WeChatPayConstants.API_V3_KEY.getBytes(StandardCharsets.UTF_8));
            // 从证书管理器中获取verifier
            verifier = certificatesManager.getVerifier(WeChatPayConstants.MCH_ID);

            return verifier.verify(serialNumber, message.getBytes(StandardCharsets.UTF_8), signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 回调数据解密
     */
    public static String decryptOrder(String body) {
        try {
            AesUtil util = new AesUtil(WeChatPayConstants.API_V3_KEY.getBytes("utf-8"));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(body);
            JsonNode resource = node.get("resource");

            String ciphertext = resource.get("ciphertext").textValue();
            String associatedData = resource.get("associated_data").textValue();
            String nonce = resource.get("nonce").textValue();

            return util.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    ciphertext
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付超时关单处理
     */
    public static void closeOrder(String orderNo) {
        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(WeChatPayConstants.PRIVATE_KEY);

            // 微信支付证书会定期更新
            // 获取证书管理器实例
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(WeChatPayConstants.MCH_ID, new WechatPay2Credentials(WeChatPayConstants.MCH_ID,
                            new PrivateKeySigner(WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                    WeChatPayConstants.API_V3_KEY.getBytes(StandardCharsets.UTF_8));
            // 从证书管理器中获取verifier
            Verifier verifier = certificatesManager.getVerifier(WeChatPayConstants.MCH_ID);

            // 构造httpclient
            CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(WeChatPayConstants.MCH_ID, WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier))
                    .build();

            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + orderNo + "/close");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid", WeChatPayConstants.MCH_ID);

            objectMapper.writeValue(bos, rootNode);

            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 204) {
                log.info("关闭微信小程序支付交易订单成功，oubOrderNo -> {}", orderNo);
            } else {
                log.error("关闭微信小程序支付交易订单失败，oubOrderNo -> {}", orderNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据商户订单号查询交易订单
     */
    public static void getTradeOrder(String orderNo) {
        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(WeChatPayConstants.PRIVATE_KEY);

            // 微信支付证书会定期更新
            // 获取证书管理器实例
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(WeChatPayConstants.MCH_ID, new WechatPay2Credentials(WeChatPayConstants.MCH_ID,
                            new PrivateKeySigner(WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                    WeChatPayConstants.API_V3_KEY.getBytes(StandardCharsets.UTF_8));
            // 从证书管理器中获取verifier
            Verifier verifier = certificatesManager.getVerifier(WeChatPayConstants.MCH_ID);

            // 构造httpclient
            CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(WeChatPayConstants.MCH_ID, WeChatPayConstants.MCH_SERIAL_NO, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier))
                    .build();

            URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + orderNo + "mchid=" + WeChatPayConstants.MCH_ID);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader("Accept", "application/json");

            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 交易订单数据
            String bodyAsString = EntityUtils.toString(response.getEntity());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
