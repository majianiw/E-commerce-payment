package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;

/**
 * @Date: create in 10:35 2023/2/27
 * @describe:
 */
public interface IOrderService {

    //shipping收货地址
    ResponseVo<OrderVo> create(Integer uid,Integer shippingId);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uid,Long orderNo);

    ResponseVo cancel(Integer uid, Long orderNo);

    //修改订单状态
    void paid(Long orderNo);
}
