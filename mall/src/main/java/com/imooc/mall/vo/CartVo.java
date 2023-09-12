package com.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Date: create in 16:17 2023/2/20
 * @describe:购物车
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectAll;

    private BigDecimal cartTotalPrice;

    //商品数量
    private Integer cartTotalQuantity;
}
