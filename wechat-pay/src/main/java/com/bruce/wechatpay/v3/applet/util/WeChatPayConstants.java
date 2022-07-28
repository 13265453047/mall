package com.bruce.wechatpay.v3.applet.util;

/**
 * @author rcy
 * @version 1.1.0
 * @className: WXConstants
 * @date 2022-07-27
 */
public class WeChatPayConstants {

    // 支付成功回调地址
    public static String NOTIFY_URL = "https://www.weixin.qq.com/pay/notify/wx/applet";
    // appid
    public static String APP_ID = "";

    // 商户号
    public static String MCH_ID = "";
    // 商户证书序列号
    public static String MCH_SERIAL_NO = "";
    // api秘钥
    public static String API_V3_KEY = "";

    // 商户秘钥：apiclient_key.pem这个文件里面的内容
    public static String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDt48a9MiBZfcpN\n" +
            "qV1RnCMSDE4kcxzVdqWIBEOG5jG4xQLRiNnC0js2DhL7631hAzsoVV6pNa1UdxXX\n" +
            "nO/v32abb7QDwPsKgeoHU/IL7VKzABMrNMTwUTPjL8YYludniHmWcxqvEzkk4fNf\n" +
            "xaW8Al4ZK5xeV4jjOibNI50M5eC5powR6jUlQVgjJHfhKp2mtxSQIgT3Gnw6cs/l\n" +
            "JiT6rC57E0kmpVQnP8pHxCVZRV8a6cu5OFZc5n2yQU2KsD8Gi71uMucczX6VP2XA\n" +
            "mYvi9J9OM1kvLza7aoT4GYtPqczmvHpdM5/GvanixN634hMbJm3d0kvHFVnBlv1X\n" +
            "+xUbSf4HAgMBAAECggEBANyZyySLpWAXvFDEbBtP9V25N4WpkeWJ/vrhKVWhhh5X\n" +
            "48M3aui1V99KseQXfs9F1cgTUpMLic64F/M4P3Br9JTTw1Vv0dzumH1vJRhWXchM\n" +
            "m3oHl1FbnAhjtwn6dmCWOFqCJw6PfwBq3+vCN2zfrS8eOAVsmy5MMMsu6WaajgjZ\n" +
            "VWRH4fjolyghR/zAxGJ6MeQJ+zcDRPuzr43Z/SsAPbxNg7nWE3fLcewhFi7m5xYU\n" +
            "SBZzqI7c7u+dGAoGNAsiBaMufPklnKkyI/jyzycsvgKHtdxSYt4/kyDingCPs3s4\n" +
            "2WlC1y0nfz9Zb5l5ZP/nw80GptZuP75mxZpiEvetftECgYEA++1Yyc1ZiVwGey9w\n" +
            "yAu5hg0zaOSkXoQtJbJI3eFCfL2diSoiGu6lF3CjzJkBEaXK4zt9Ck3RMqJX8nJd\n" +
            "wSVYjMUtwUKA+N+blCbfURPe+Sn5xFVGjjKUxSvQA+J/2f5sfuvSzsaT/AzaXsLb\n" +
            "0WfrI1lDRWN+mdjWkGuktb55MDMCgYEA8bxVNQ0zo0UUqz6ZjgQRmO6IRSRwBEHM\n" +
            "Z0rCNHwrTn7PF+IvtnzkBQH+w8LU7b2o4MeMreJB+npfsKSGOixsZg8eM78cLrRo\n" +
            "Exj8rK9f5h8UIdiaGhInrFXJfuvFVUDFGV3mOjdXajDDvg9ScFVi2sDEjkhWxabt\n" +
            "ewNFSkBdFt0CgYA8sZDbhXKVyirxkJteRpzas+F32uXSs4tg8pPliLiI7vG+Mv1m\n" +
            "eaoifIup5g7A3q2DkzhrvBWzWjNQAMefmcy8lWCRl+EUlGKaDcdtdBka9EbtSxIl\n" +
            "fgAjhIun8/CTZ11iMpRmJqdOaRZPECj/SUb4DDBNzkuY6wm+X4CeHJZ7ywKBgQDh\n" +
            "djuuqGqCHFtDwLYte+QB1HBqtKp5ffksWdC6bRljM+jpqL/Bn8h4Vz0LzrsUJjHN\n" +
            "vLCcOYw6F0sKkNjUhzxvECimMNgTQ5ZSQZrhYQy7sD7xtnk8WbSBCt5NL4+0jR2I\n" +
            "JEkoARfZ5jObH+VO+oSh7OsmnZe475FNi2Zj85YfTQKBgQCcYD8YcUtcF461wJ7H\n" +
            "E7LXHwbky+gTC7vov5c4mghFL0W489bAwf4qBDB8rF5AlLYIHTbAJFbpYRAjGmpV\n" +
            "tfoG2PAT7OuAbzBWBd2atvswiKFQ8fS/VzFRLpusYEW2fsihpzGqtBfBJGnj14bG\n" +
            "W6a6kuJenw559JpvCF0i0Vu1FQ==\n" +
            "-----END PRIVATE KEY-----";

    /**
     * JSAPI下单
     */
    public static final String JSAPIORDER_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";


}
