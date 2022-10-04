package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.utils.Result;
import com.xiaozhi.dto.SetmealDto;
import com.xiaozhi.pojo.Category;
import com.xiaozhi.pojo.Setmeal;
import com.xiaozhi.service.CategoryService;
import com.xiaozhi.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20232
 */
@Api(value = "SetmealController", tags = {"套餐管理控制器"})
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;


//    http://localhost:8088/setmeal/page?page=1&pageSize=10


    @GetMapping("/page")
    public Result<IPage> getSetmealPage(@RequestParam Integer page, @RequestParam Integer pageSize, String name) {
        //        构建分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);

        Page<SetmealDto> setmealDtoPage = new Page<>();
        IPage setmealList = setmealService.getSetmealList(pageInfo, name);


        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> setmealRecords = pageInfo.getRecords();
        List<SetmealDto> setmealDtoList = setmealRecords.stream().map((item) -> {
            log.warn("iteam:{}", item);
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long id = item.getCategoryId();
            Category category = categoryService.getById(id);

            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoList);
        return Result.success(setmealDtoPage);
    }


    /**
     * 添加套餐信息
     *
     * @param setmealDto
     * @return
     */
    @ApiOperation("添加套餐信息接口")
    @PostMapping
    public Result<String> saveInfo(@RequestBody SetmealDto setmealDto) {
        log.info("dto:" + setmealDto);
        //        调用service层方法
        setmealService.saveWithDish(setmealDto);
        return Result.success("添加成功");
    }


//    http://localhost:8080/setmeal/1569888794120605698
//

    /**
     * 根据id回显数据到修改页面
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> getSetmealInfo(@PathVariable Long id) {

        SetmealDto setmealDto = setmealService.getInfo(id);

        return Result.success(setmealDto);
    }


//    http://localhost:8080/setmeal

    @PutMapping
    public Result<String> updateSetmealInfo(@RequestBody SetmealDto setmealDto) {

        setmealService.updateInfo(setmealDto);

        return Result.success("修改成功！");
    }


    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return Result.success("删除成功");
    }


    /**
     *  http://localhost:8080/setmeal/list?categoryId=1413386191767674881&status=1
     */


    /**
     * @param categoryId categoryId
     * @param status     状态
     * @return
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> getClientCategoryList(@RequestParam String categoryId, @RequestParam Integer status) {
        log.info("id:{},status:{}", categoryId, status);
        if (categoryId != null && status == 1) {
            List<Setmeal> setmealList = setmealService.getCategoryInfo(categoryId, status);
            return Result.success(setmealList);
        }
        return Result.error("发生错误");
    }


}
