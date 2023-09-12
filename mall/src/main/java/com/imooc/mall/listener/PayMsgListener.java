package com.imooc.mall.listener;

import com.google.gson.Gson;
import com.imooc.mall.pojo.PayInfo;
import com.imooc.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Date:  create in 14:34 2023/3/1
 * @describe:   支付传过来的消息
 * 关于PayInfo这个对象，正确做法是pay项目提供client.jar包，mall项目引用jar包，但是涉及到了多模块（springCloud）比较麻烦 所以就直接复制过来了
 */

@Component
@RabbitListener(queues="payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    IOrderService orderService;
    @RabbitHandler
    public void process(String msg){
        log.info("接收到消息,msg={}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
