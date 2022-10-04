package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.utils.Result;
import com.xiaozhi.pojo.Category;
import com.xiaozhi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 20232
 * <a href="http://localhost:8088/category">...</a>
 */

@Api(value = "CategoryController", tags = {"分类管理控制器"})
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询，url查询路径 <a href="http://localhost:8088/category/page?page=1&pageSize=10">...</a>
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取分类列表接口")
    @GetMapping("/page")
    public Result<IPage> getCategoryList(@ApiParam(name = "page", value = "当前页码", required = true)
                                         @RequestParam("page") Integer page,
                                         @ApiParam(name = "pageSize", value = "分页显示条数", required = true)
                                         @RequestParam("pageSize") Integer pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
        //分页查询
        IPage categoryList = categoryService.selectCategoryList(page1);
        return Result.success(categoryList);
    }

    /**
     * 添加菜品分类
     *
     * @param category
     * @return
     */
    @ApiOperation(value = "添加菜品/套餐分类接口")
    @PostMapping
    public Result insertCategory(@ApiParam(name = "Category", value = "Category实体类，只需要传入name和sort")
                                 @RequestBody Category category) {
        categoryService.save(category);
        return Result.success("成功");
    }


    /**
     * 根据id来修改
     *
     * @param category
     * @return
     */
    @ApiOperation(value = "根据id修改分类信息")
    @PutMapping
    public Result updateCategory(@ApiParam(name = "Category", value = "Category实体类，只需要传入id,name和sort")
                                 @RequestBody Category category) {
        log.info("正在修改，id = " + category.getId());
        //      根据id进行修改
        categoryService.updateById(category);
        return Result.success("修改成功");
    }


    /**
     * 根据id进行删除
     *
     * @param id 商品的id
     * @return
     */
    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping
    public Result deleteCategory(@ApiParam(name = "ids", value = "Category的id值")
                                 @RequestParam("ids") Long id) {
        //        categoryService.removeById(id);
        categoryService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 根据查询条件查询数据
     * @param category
     * @return
     */
    @ApiOperation(value = "添加菜品分类列表接口")
    @GetMapping("/list")
    public Result<List<Category>> selectType(@ApiParam(name = "type", value = "菜品分类的type，默认为1")
                                            Category category) {
        Integer type = category.getType();
        List<Category> categoryList = categoryService.selectByType(type);
        return Result.success(categoryList);
    }

}
