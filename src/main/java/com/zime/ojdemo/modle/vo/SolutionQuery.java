package com.zime.ojdemo.modle.vo;

import lombok.Data;

@Data
public class SolutionQuery {
    String userId;
    String problemId;
    Integer language;
    String contestId;
    String courseId;
    Integer result;
}
