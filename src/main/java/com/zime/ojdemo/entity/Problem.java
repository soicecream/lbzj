package com.zime.ojdemo.entity;

import java.math.BigDecimal;

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
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "problem_id", type = IdType.AUTO)
    private Integer problemId;

    private String title;

    private String description;

    private String input;

    private String output;

    private String samples;

    private String sampleInput;

    private String sampleOutput;

    private String spj;

    private String hint;

    private String source;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private Date inDate;

    private BigDecimal timeLimit;

    private Integer memoryLimit;

    private Boolean defunct;

    private Integer accepted;

    private Integer submit;

    private Integer solved;

    private Integer degree;

    private Integer ordernum;

    private String author;

}
