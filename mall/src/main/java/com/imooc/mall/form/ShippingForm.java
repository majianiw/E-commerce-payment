package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Date: create in 14:43 2023/2/26
 * @describe:
 */
@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String receiverMobile;
    @NotBlank
    private String receiverProvince;
    @NotBlank
    private String receiverCity;
    @NotBlank
    private String receiverDistrict;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    private String receiverZip;
}
