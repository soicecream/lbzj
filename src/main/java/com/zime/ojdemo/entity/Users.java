package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    private String email;

    private Integer submit;

    private Integer solved;

    private Boolean defunct;

    private String ip;


    private Date accesstime;

//    private Integer rating;
//
//    private String signature;

    private  int volume;

    private String password;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private Date regTime;

    private String nick;

    private String school;

    private String avatar;

    private String introduce;

    private String js;


}
