package com.zime.ojdemo.entity.Dto.Notice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class NOTICELIST {

    private Integer id;

    private String title;

    @TableField(exist = false)
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //是前端往后段传的时候使用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //后端往前端传输的时候使用
    private Date createTime;

    private Boolean isSetTop;
}
