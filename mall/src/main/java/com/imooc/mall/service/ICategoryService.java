package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Date: create in 15:25 2023/2/18
 * @describe:
 */
@Service
public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
