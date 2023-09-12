package com.imooc.mall.enums;

import lombok.Getter;

/**
 * @Date: create in 14:00 2023/2/27
 * @describe:
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1);

    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
