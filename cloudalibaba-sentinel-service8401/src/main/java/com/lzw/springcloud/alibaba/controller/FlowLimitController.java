package com.lzw.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){

        //暂停毫秒
//        try{
//            TimeUnit.MILLISECONDS.sleep(800);
//        }
//        catch(InterruptedException e){
//            e.printStackTrace();
//        }
        log.info(Thread.currentThread().getName() + "------testA");
        return "------testA";

    }

    @GetMapping("/testB")
    public String testB(){

        return "------testB";

    }

    @GetMapping("/testD")
    public String testD(){

        //暂停毫秒
        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        log.info(Thread.currentThread().getName() + "请求testD");
        return "------testD";

    }

}
