package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Artical;
import com.example.entity.User;
import com.example.service.ArticalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/artical")
public class ArticalController {
    @Resource
    private ArticalService articalService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    @PostMapping
    public Result<?> save(@RequestBody Artical artical) {
        artical.setUsername(getUser().getUsername());
        artical.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(articalService.save(artical));
    }

    @PutMapping
    public Result<?> update(@RequestBody Artical artical) {
        artical.setUsername(getUser().getUsername());
        artical.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(articalService.updateById(artical));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        articalService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(articalService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(articalService.list());
    }

    @GetMapping("/range/all")
    public Result<?> findAllRange() {
        return Result.success(articalService.findAllRange());
    }

    @GetMapping("/range/week")
    public Result<?> findWeekRange() {
        return Result.success(articalService.findWeekRange());
    }


    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") String flag,
                              @RequestParam(required = false, defaultValue = "") String category,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Artical> query = Wrappers.<Artical>lambdaQuery().like(Artical::getTitle, name).orderByDesc(Artical::getId);
        // 角色2是普通用户，这里对角色2的用户做一个数据的筛选，只看他自己的数据
        if ("end".equals(flag) && getUser().getRole().contains(2)) {
            query.and(wrapper -> wrapper.eq(Artical::getUsername, getUser().getUsername()));
        }
        if (StrUtil.isNotBlank(category)) {
            query.and(wrapper -> wrapper.eq(Artical::getCategory, category));
        }
        return Result.success(articalService.page(new Page<>(pageNum, pageSize), query));
    }

}
