package com.lzw.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.lzw.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){

        return "线程池: " + Thread.currentThread().getName() + " paymentInfo_OK, id: " + id;

    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){

        int timeNumber = 5000;
//        int age = 10/0;

        try {
            TimeUnit.MILLISECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池: " + Thread.currentThread().getName() + " paymentInfo_TimeOut, id: " + id + ", 耗时"+ timeNumber +"秒钟";

    }

    public String paymentInfo_TimeOutHandler(Integer id){

        return "线程池: " + Thread.currentThread().getName() + " 8001系统繁忙，请稍后再试, id: " + id + " o_0";

    }

    // ******服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求总数阈值，在快照时间窗内，必须满足请求总数阈值才有资格熔断。默认为20，
            // 意味着在10秒内如果该hystrix命令的调用次数不足20，即使所有的请求都超时或其他原因失败，断路器都不会打开
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 快照时间窗，断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 错误百分比阈值，当请求总数在快照时间窗内超过了阈值，比如发生了30次调用，如果在这30次调用中，
            // 有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阈值情况下，这时就会将断路器打开
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){

        if(id < 0){
            throw new RuntimeException("******id 不能为负数");
        }

        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + " 调用成功，流水号: " + uuid;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){

        return "id不能为负数，请稍后再试， id：" + id;

    }

}
