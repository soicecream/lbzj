package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-02-18
 */
@Getter
@Setter
@TableName("sys_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    @TableField(exist = false)
    private String creator;
    private Long creatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //是前端往后段传的时候使用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //后端往前端传输的时候使用
    private Date createTime;

    @TableField(exist = false)
    private String updator;
    private Long updatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //是前端往后段传的时候使用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //后端往前端传输的时候使用
    private Date updateTime;

    private Boolean state;

    private Boolean isSetTop;

}
