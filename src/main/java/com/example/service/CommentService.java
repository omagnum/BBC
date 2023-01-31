package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.entity.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserService userService;

    public List<Comment> findByArtical(Long articalId) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery().eq(Comment::getActicalId, articalId).orderByDesc(Comment::getId);
        List<Comment> list = list(queryWrapper);
        for (Comment comment : list) {
            User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, comment.getUsername()));
            comment.setAvatar("http://localhost:9999/files/" + one.getAvatar());
            Long parentId = comment.getParentId();
            list.stream().filter(c -> c.getId().equals(parentId)).findFirst().ifPresent(comment::setParentComment);
        }
        return list;
    }
}
