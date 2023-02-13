package com.zime.ojdemo.modle.vo.query;


import lombok.Data;

@Data
public class ProblemQuery {

    String id;
    String userId;

    Integer problemId;

    String title;

    Integer degree;

    Boolean defunct;

}
