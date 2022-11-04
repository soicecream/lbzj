package com.zime.ojdemo.modle.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemListResult implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer problemId;

    private String title;

    private Integer accepted;

    private Integer submit;

    private Integer solved;

    private Integer degree;

    private Integer ordernum;

    Integer state;

    private Boolean defunct;
}
