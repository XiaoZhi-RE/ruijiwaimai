package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.pojo.DishFlavor;
import com.xiaozhi.service.DishFlavorService;
import com.xiaozhi.utils.Result;
import com.xiaozhi.dto.DishDto;
import com.xiaozhi.pojo.Category;
import com.xiaozhi.pojo.Dish;
import com.xiaozhi.service.CategoryService;
import com.xiaozhi.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20232
 * 菜品管理控制器
 */
@Api(value = "DishController", tags = {"菜品管理控制器"})
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;


    /**
     * 请求路径：<a href="http://localhost:8088/dish/page?page=1&pageSize=10&name=">...</a>?，请求参数要和路径一样
     *
     * @param page     当前页
     * @param pageSize 查询页大小
     * @param name     查询条件，请求参数
     * @return Result
     */
    @ApiOperation("菜品管理分页查询接口")
    @GetMapping("/page")
    public Result<IPage> getDishList(@ApiParam(name = "page", value = "当前页码") @RequestParam("page") Integer page,
                                     @ApiParam(name = "pageSiz", value = "页面大小") @RequestParam("pageSize") Integer pageSize,
                                     @ApiParam(name = "name", value = "查询条件名字") String name) {
        Page<Dish> page1 = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();


        IPage dishList = dishService.getDishList(page1, name);
        //       打印日志
        log.info(dishList.toString());


        //        属性拷贝,忽略records(查询记录)
        BeanUtils.copyProperties(page1, dishDtoPage, "records");

        List<Dish> records = page1.getRecords();
        //        获得dish对应的categoryName
        List<DishDto> dishDtoList = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);
        log.info(dishDtoList.toString());

        return Result.success(dishDtoPage);
    }


    /**
     * 添加菜品管理接口
     *
     * @param dishDto
     * @return
     */
    @ApiOperation("添加菜品管理接口")
    @PostMapping
    public Result<String> insertDishList(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return Result.success("添加成功");
    }


    /**
     * 根据id查询数据，回显
     *
     * @param id
     * @return
     */
    @ApiOperation("查询菜品信息接口")
    @GetMapping("{id}")
    public Result<DishDto> getDishInfoById(@PathVariable Long id) {
        return Result.success(dishService.getDisInfo(id));
    }


    /**
     * 修改菜品信息
     *
     * @param dishDto
     * @return
     */
    @ApiOperation("修改菜品分类接口")
    @PutMapping
    public Result<String> updateDishInfo(@RequestBody DishDto dishDto) {
        log.info("dishId为" + dishDto.getId());
        dishService.updateByDishId(dishDto);
        return Result.success("修改成功");

    }


    /**
     * 根据id删除
     * <a href="http://localhost:8088/dish?ids=1565557214350737410">...</a>
     *
     * @param ids id
     * @return Result
     */
    @ApiOperation("根据ids删除接口")
    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam List<Long> ids) {
        //       根据id 批量删除
        dishService.removeDish(ids);
        return Result.success("删除成功");
    }


    /**
     * 批量启用接口
     *
     * @param status
     * @param ids
     * @return
     */
    @ApiOperation("批量启用/停售接口")
    @PostMapping("/status/{status}")
    public Result<String> setDishStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        log.warn("请求状态status:" + status);

        //        修改update的包装器
        UpdateWrapper<Dish> dishLambdaUpdateWrapper = new UpdateWrapper<>();

        List<Dish> dishList = dishService.selectByIds(ids);
        log.warn("disList======" + dishList);

        dishList.forEach(dish -> {
            if (status == 1) {
                dishLambdaUpdateWrapper.set("status", 0).in("id", dish.getId());
            }
            if (status == 0) {
                dishLambdaUpdateWrapper.set("status", 1).in("id", dish.getId());
            }
            dishService.update(dishLambdaUpdateWrapper);
        });
        //        根据id修改

        return Result.success("成功");
    }


    /**
     * @param dish
     * @return Result
     * 请求 URL:<a href="http://localhost:8088/dish/list?categoryId=1397844263642378242">...</a>
     */


    @ApiOperation("根据id查询菜品信息列表")
    @GetMapping("/list")
    public Result<List<DishDto>> getList(Dish dish) {
        log.warn("dish为" + dish);

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //        设置查询条件,根据id查询
        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //        设置查询条件,根据name查询
        lambdaQueryWrapper.eq(dish.getName() != null, Dish::getName, dish.getName());
        //        设置查询条件,商品状态，0是停售，1是起售
        lambdaQueryWrapper.eq(Dish::getStatus, 1);
        //        设置排序规则
        lambdaQueryWrapper.orderByAsc(Dish::getStatus).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lambdaQueryWrapper);
        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getId();
            if (id != null) {
                LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper1.eq(DishFlavor::getDishId, id);
                List<DishFlavor> flavorList = dishFlavorService.list(lambdaQueryWrapper1);
                //设置口味数据，DisDto封装了一份disFlavors
                dishDto.setFlavors(flavorList);
            }
            return dishDto;

        }).collect(Collectors.toList());

        return Result.success(dishDtoList);
    }


}
