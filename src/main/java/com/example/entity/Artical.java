package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("t_artical")
public class Artical extends Model<Artical> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 标题
      */
    private String title;

    /**
      * 内容
      */
    private String content;

    /**
      * 阅读次数
      */
    private Integer readCount;

    /**
      * 发布人
      */
    private String username;

    /**
      * 发布时间
      */
    private String time;

    /**
      * 标签
      */
    private String tag;

    private String category;

    private String description;

    private String cover;

}
