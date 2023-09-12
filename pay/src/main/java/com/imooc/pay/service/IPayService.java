package com.imooc.pay.service;

import com.imooc.pay.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @Date: create in 18:53 2023/2/11
 * @describe:
 */
public interface IPayService {
    /**
    * @Description: 创建支付
    * @Date: 2023/2/11 18:54
    */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    //异步通知处理
    String asyncNotify(String notifyData);

    //通过订单号查询支付记录
    PayInfo queryByOrderId(String orderId);
}
