package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Artical;
import com.example.entity.Comment;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Comment comment) {
        comment.setUsername(getUser().getUsername());
        comment.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(commentService.save(comment));
    }

    @PutMapping
    public Result<?> update(@RequestBody Comment comment) {
        return Result.success(commentService.updateById(comment));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/artical/{articalId}")
    public Result<?> findByArtical(@PathVariable Long articalId) {
        return Result.success(commentService.findByArtical(articalId));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") String flag,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Comment> query = Wrappers.<Comment>lambdaQuery().like(Comment::getContent, name).orderByDesc(Comment::getId);
        if ("end".equals(flag) && getUser().getRole().contains(2)) {
            query.and(wrapper -> wrapper.eq(Comment::getUsername, getUser().getUsername()));
        }
        return Result.success(commentService.page(new Page<>(pageNum, pageSize), query));
    }

}
