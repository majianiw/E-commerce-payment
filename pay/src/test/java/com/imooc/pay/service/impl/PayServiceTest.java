//package com.imooc.pay.service.impl;
//
//import com.imooc.pay.service.PayApplicationTests;
//import com.lly835.bestpay.enums.BestPayTypeEnum;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//
//
//class PayServiceTest extends PayApplicationTests {
//
//    @Autowired
//    private PayServiceImpl payService;
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Test
//    public void create() {
//        payService.create("1680510659344", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
//    }
//
//    @Test
//    public void sendMqMsg(){
//        amqpTemplate.convertAndSend("payNotify","hello");
//    }
//}