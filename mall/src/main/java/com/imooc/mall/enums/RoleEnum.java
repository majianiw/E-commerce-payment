package com.imooc.mall.enums;

/**
 * @Date: create in 13:03 2023/2/14
 * @describe:
 */
public enum RoleEnum {
    ADMIN(0),

    CUSTOMER(1),;
    Integer code;

    public Integer getCode() {
        return code;
    }


    RoleEnum(Integer code){
        this.code = code;
    }


}
