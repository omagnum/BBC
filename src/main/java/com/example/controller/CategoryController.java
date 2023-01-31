package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Category;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @PostMapping
    public Result<?> save(@RequestBody Category category) {
        LambdaQueryWrapper<Category> query = Wrappers.<Category>lambdaQuery().eq(Category::getName, category.getName());
        List<Category> list = categoryService.list(query);
        if (CollUtil.isNotEmpty(list)) {
            throw new CustomException("-1", "名称重复");
        }
        return Result.success(categoryService.save(category));
    }

    @PutMapping
    public Result<?> update(@RequestBody Category category) {
        return Result.success(categoryService.updateById(category));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Category> query = Wrappers.<Category>lambdaQuery().like(Category::getName, name);
        return Result.success(categoryService.page(new Page<>(pageNum, pageSize), query));
    }

}
