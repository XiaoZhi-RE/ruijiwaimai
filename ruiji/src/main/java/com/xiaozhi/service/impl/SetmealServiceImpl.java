package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.common.CustomException;
import com.xiaozhi.dto.SetmealDto;
import com.xiaozhi.mapper.SetmealMapper;
import com.xiaozhi.pojo.Setmeal;
import com.xiaozhi.pojo.SetmealDish;
import com.xiaozhi.service.SetmealDishService;
import com.xiaozhi.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20232
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {


    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public IPage<Setmeal> getSetmealList(Page<Setmeal> pageInfo, String name) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        return baseMapper.selectPage(pageInfo, lambdaQueryWrapper);
    }

    @Override
    public SetmealDto getInfo(Long id) {
        //        获取setmeal对象
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        //        拷贝属性到setmealDto
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());

        List<SetmealDish> setmealDtoList = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealDtoList);

        return setmealDto;
    }

    @Override
    public void updateInfo(SetmealDto setmealDto) {
//        更新表
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);

    }

    @Override
    public void removeWithDish(List<Long> ids) {

//        select * from setmeal where id in () and status = 1;

        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        long count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("套餐正在售卖，无法删除");
        }
        this.removeBatchByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(queryWrapper);


    }

    @Override
    public List<Setmeal> getCategoryInfo(String categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Setmeal::getCategoryId, categoryId).eq(Setmeal::getStatus, status);
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}




