package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("t_comment")
public class Comment extends Model<Comment> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 内容
      */
    private String content;

    /**
      * 评论人
      */
    private String username;

    /**
      * 评论时间
      */
    private String time;

    /**
      * 父ID
      */
    private Long parentId;

    @TableField(exist = false)
    private Comment parentComment;

    /**
     * 文章id
     */
    private Long acticalId;
    @TableField(exist = false)
    private String avatar;

}
