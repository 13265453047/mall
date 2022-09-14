https://blog.csdn.net/lyh1023812/article/details/123898142

hystrix:
### 三种降级方案
1. 熔断触发降级
```java
commandProperties = {
    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启熔断
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), // 最小请求次数
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), // 时间窗口，熔断的时长
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), // 错误率
}
```
2. 请求超时触发降级
```java
commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
}
```
3. 通过隔离（信号量/线程资源）触发降级：比如可以针对每一个远端服务调用（order-service）设置相应的线程池，如果线程池资源耗尽了，则触发降级。防止请求某个服务占用过多的资源


