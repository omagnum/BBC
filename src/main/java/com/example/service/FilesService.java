package com.example.service;

import com.example.entity.Files;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.FilesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FilesService extends ServiceImpl<FilesMapper, Files> {

    @Resource
    private FilesMapper filesMapper;

}
