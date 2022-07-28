package com.bruce.wechatpay.v3.applet.controller;

import com.bruce.wechatpay.v3.applet.service.WxOnlinePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rcy
 * @data 2021-09-21 22:56
 * @description 线上支付回调处理
 */
@Slf4j
@RestController
@RequestMapping(value = "/pay/notify")
public class OnlinePayNotifyController {

    @Autowired
    private WxOnlinePayService wxPayService;

    /**
     * 微信小程序支付（V3）回调
     *
     * @return
     */
    @PostMapping("/wx/applet")
    public void wxPay(HttpServletRequest request, HttpServletResponse response) {
        wxPayService.callback(request, response);
    }

}
