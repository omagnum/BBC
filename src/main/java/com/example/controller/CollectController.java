package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Collect;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.CollectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/collect")
public class CollectController {
    @Resource
    private CollectService collectService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @PostMapping
    public Result<?> save(@RequestBody Collect collect) {
        LambdaQueryWrapper<Collect> query = Wrappers.<Collect>lambdaQuery().eq(Collect::getName, collect.getName()).eq(Collect::getUsername, getUser().getUsername());
        List<Collect> list = collectService.list(query);
        if (CollUtil.isNotEmpty(list)) {
            throw new CustomException("-1", "该文章已收藏");
        }
        collect.setUsername(getUser().getUsername());
        collect.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(collectService.save(collect));
    }

    @PutMapping
    public Result<?> update(@RequestBody Collect collect) {
        return Result.success(collectService.updateById(collect));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        collectService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(collectService.getById(id));
    }

    @GetMapping("/blog/{username}/{articleName}")
    public Result<?> findById(@PathVariable String username, @PathVariable String articleName) {
        LambdaQueryWrapper<Collect> query = Wrappers.<Collect>lambdaQuery().eq(Collect::getName, articleName).eq(Collect::getUsername, username);
        List<Collect> list = collectService.list(query);
        return Result.success(list.size() > 0);
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(collectService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Collect> query = Wrappers.<Collect>lambdaQuery().like(Collect::getName, name);
        return Result.success(collectService.page(new Page<>(pageNum, pageSize), query));
    }

}
