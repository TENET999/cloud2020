package com.lzw.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderConsulController {

    //单机版，地址写死了
    //public static final String PAYMENT_URL = "http://localhost:8001";
    //集群版
    public static final String INVOKE_URL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/consul")
    public String paymentInfo(){

        String result = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
        return result;

    }

}
