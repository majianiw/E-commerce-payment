package com.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date: create in 9:27 2023/2/19
 * @describe:
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
