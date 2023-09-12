package com.imooc.mall.enums;

/**
 * @Date: create in 23:11 2023/2/20
 * @describe: 商品状态枚举
 */
public enum ProductStatusEnum {
//1在售，2下架，3删除
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3)
    ;
    Integer code;

    public Integer getCode() {
        return code;
    }

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
