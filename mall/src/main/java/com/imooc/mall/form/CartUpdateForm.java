package com.imooc.mall.form;

import lombok.Data;

/**
 * @Date: create in 9:51 2023/2/26
 * @describe:
 */
@Data
public class CartUpdateForm {
    //直接更新为quantity 而不是进行加减运算
    private Integer quantity;

    private Boolean selected;
}
