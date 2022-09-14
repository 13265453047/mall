package com.bruce.hystrix.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.client.RestTemplate;

/**
 * 使用实现 HystrixCommand 来设置 断路器配置
 *
 * @author rcy
 * @version 1.1.0
 * @className: MyHystrixCommand
 * @date 2022-09-14
 */
public class HystrixCommandService extends HystrixCommand<String> {

    private int orderNo;
    private RestTemplate restTemplate;

    public HystrixCommandService(int orderNo, RestTemplate restTemplate) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("spring-cloud-hystrix-service"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutEnabled(true)
                                .withExecutionTimeoutInMilliseconds(5000)
                )
        );
        this.orderNo = orderNo;
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://localhost:7071/api/order/" + orderNo, String.class);
    }

    @Override
    protected String getFallback() {
        return "进入服务降级处理";
    }

}
