package com.example.mapper;

import com.example.entity.Artical;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ArticalMapper extends BaseMapper<Artical> {

    List<Artical> findAllRange();

}
