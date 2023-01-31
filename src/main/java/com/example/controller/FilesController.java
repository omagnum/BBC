package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Files;
import com.example.service.FilesService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FilesController {
    @Resource
    private FilesService filesService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @PostMapping
    public Result<?> save(@RequestBody Files files) {
        files.setUsername(getUser().getUsername());
        files.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(filesService.save(files));
    }

    @PutMapping
    public Result<?> update(@RequestBody Files files) {
        files.setUsername(getUser().getUsername());
        files.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(filesService.updateById(files));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        filesService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(filesService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(filesService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Files> query = Wrappers.<Files>lambdaQuery().like(Files::getName, name);
        return Result.success(filesService.page(new Page<>(pageNum, pageSize), query));
    }

}
