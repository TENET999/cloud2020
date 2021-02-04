package com.lzw.springcloud.alibaba.controller;

import com.lzw.springcloud.entities.CommonResult;
import com.lzw.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();

    static{

        hashMap.put(1L, new Payment(1L, "serial111"));
        hashMap.put(2L, new Payment(2L, "serial222"));
        hashMap.put(3L, new Payment(3L, "serial333"));

    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){

        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult(200, "serverPort: " + serverPort, payment);
        return result;

    }

}
