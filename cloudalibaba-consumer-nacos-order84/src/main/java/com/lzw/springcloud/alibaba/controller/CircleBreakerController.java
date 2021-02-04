package com.lzw.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lzw.springcloud.entities.CommonResult;
import com.lzw.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") // 没有配置
//    @SentinelResource(value = "fallback", fallback = "handlerFallback") // 只配置fallback，只负责业务异常
    @SentinelResource(value = "fallback", blockHandler = "blockHandler")    // 只配置blockHandler，只负责sentinel控制台配置的流控违规
    public CommonResult<Payment> fallback(@PathVariable Long id){

        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常……");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException, 该ID没有对应记录, 空指针异常");
        }

        return result;

    }
    // 此为fallback
    public CommonResult handlerFallback(@PathVariable Long id, Throwable e){

        Payment paymen = new Payment(id, "null");
        return new CommonResult(444, "兜底的异常内容: " + e.getMessage(), paymen);

    }

    // 此为blockHandler
    public CommonResult blockHandler(@PathVariable Long id, BlockException blockException){

        Payment paymen = new Payment(id, "null");
        return new CommonResult(445, "限流, 无此流水: " + blockException.getMessage(), paymen);

    }

}
