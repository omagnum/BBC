package com.example.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.entity.Artical;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ArticalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticalService extends ServiceImpl<ArticalMapper, Artical> {

    @Resource
    private ArticalMapper articalMapper;

    public List<Artical> findAllRange() {
        return articalMapper.findAllRange();
    }

    public List<Artical> findWeekRange() {
        List<Artical> allRange = articalMapper.findAllRange();
        DateTime begin = DateUtil.beginOfWeek(new Date());
        DateTime end = DateUtil.endOfWeek(new Date());
        List<Artical> collect = allRange.stream().filter(r -> DateUtil.parseDateTime(r.getTime()).getTime() <= end.getTime()
                && DateUtil.parseDateTime(r.getTime()).getTime() >= begin.getTime()).collect(Collectors.toList());
        return collect;
    }
}
