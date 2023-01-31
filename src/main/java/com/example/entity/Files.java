package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("t_files")
public class Files extends Model<Files> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 名称
      */
    private String name;

    /**
      * 描述
      */
    private String description;

    /**
      * 上传时间
      */
    private String time;

    /**
      * 上传人
      */
    private String username;

    private String file;

}
