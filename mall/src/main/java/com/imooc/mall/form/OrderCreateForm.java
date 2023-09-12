package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date: create in 16:33 2023/2/28
 * @describe:
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;

}
