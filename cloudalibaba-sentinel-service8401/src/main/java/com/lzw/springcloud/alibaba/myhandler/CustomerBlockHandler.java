package com.lzw.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lzw.springcloud.entities.CommonResult;
import com.lzw.springcloud.entities.Payment;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception){

        return new CommonResult(4444, "用户自定义, global handleException------1");

    }

    public static CommonResult handlerException2(BlockException exception){

        return new CommonResult(4444, "用户自定义, global handleException------2");

    }

}
