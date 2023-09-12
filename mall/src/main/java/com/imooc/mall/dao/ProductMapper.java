package com.imooc.mall.dao;

import com.imooc.mall.pojo.Product;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCategoryIdSet(@RequestParam("categoryIdSet") Set<Integer> categoryIdSet);

    List<Product> selectByProductIdSet(@RequestParam("productIdSet") Set<Integer> productIdSet);
}