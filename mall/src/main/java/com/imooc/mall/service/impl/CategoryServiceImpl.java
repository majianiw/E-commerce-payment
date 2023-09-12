package com.imooc.mall.service.impl;

import com.imooc.mall.dao.CategoryMapper;
import com.imooc.mall.pojo.Category;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.imooc.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * @Date: create in 15:26 2023/2/18
 * @describe:
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    /**
    * @Description: 先查出1级目录 -> 查其子目录，一直查到null
    * @Date: 2023/2/18 18:48
    */
    /**
    * @Description: 循环很多 但不耗时。
     * ]
     * 最耗时的是http请求 磁盘 mysql(内网+磁盘)
    * @Date: 2023/2/18 19:58
    */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
//        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();

        //查出parent_id = 0
        /**
        for(Category category:categories){
            if(category.getParentId().equals(ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                categoryVoList.add(categoryVo);
            }
       }**/
        //e -> e.getParentId() :lambda表达式,
//        lambda+stream
        //查询一级目录
        List<CategoryVo> categoryVoList = categories.stream()
                        .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                        .map(this::category2CategoryVo)
                        .sorted(Comparator.comparing(CategoryVo::getShortOrder).reversed())
                        .collect(Collectors.toList());

        //查询子目录
        findSubCategory(categoryVoList,categories);
        return ResponseVo.success(categoryVoList);
    }


    //查询多级目录
    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categories){
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();

            for (Category category : categories) {
                //如果查到内容，设置subCategory，继续往下查
                if(categoryVo.getId().equals(category.getParentId())){
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getShortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);

                findSubCategory(subCategoryVoList,categories);
            }
        }
    }

    //category转换为categoryVo
    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet){
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categories);
    }
    //因为要递归查询多级目录，所以避免数据库多次查询,
    private void findSubCategoryId(Integer id,Set<Integer> resultSet,List<Category> categories){
        for (Category category : categories) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }
}
