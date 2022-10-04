package com.xiaozhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 20232
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service
 * @createDate 2022-07-12 10:33:12
 */
public interface CategoryService extends IService<Category> {

    IPage selectCategoryList(Page<Category> page1);

    void deleteById(Long id);

    List<Category> selectByType(Integer type);
}
