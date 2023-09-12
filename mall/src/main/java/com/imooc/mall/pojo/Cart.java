package com.imooc.mall.pojo;

import lombok.Data;

/**
 * @Date: create in 11:15 2023/2/25
 * @describe:
 */
@Data
public class Cart {
    private Integer productId;

    private Integer quantity;

    private boolean productSelected;

    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

}
