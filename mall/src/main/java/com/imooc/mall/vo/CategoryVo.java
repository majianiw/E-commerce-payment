package com.imooc.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * @Date: create in 15:23 2023/2/18
 * @describe:
 */
@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private String password;

    private Integer shortOrder;

    private List<CategoryVo> subCategories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getShortOrder() {
        return shortOrder;
    }

    public void setShortOrder(Integer shortOrder) {
        this.shortOrder = shortOrder;
    }

    public List<CategoryVo> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryVo> subCategories) {
        this.subCategories = subCategories;
    }
}
