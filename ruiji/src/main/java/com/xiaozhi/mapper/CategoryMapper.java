package com.xiaozhi.mapper;

import com.xiaozhi.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.Category
*/
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}




