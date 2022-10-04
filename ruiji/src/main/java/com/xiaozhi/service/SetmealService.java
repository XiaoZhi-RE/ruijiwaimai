package com.xiaozhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhi.dto.SetmealDto;
import com.xiaozhi.pojo.Setmeal;

import java.util.List;

/**
* @author 20232
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-07-12 10:33:12
*/
public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    IPage<Setmeal> getSetmealList(Page<Setmeal> pageInfo, String name);

    SetmealDto getInfo(Long id);

    void updateInfo(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

    List<Setmeal> getCategoryInfo(String categoryId, Integer status);

//    IPage<Setmeal> getSetmealList(Page<Setmeal> pageInfo, String name);
}
