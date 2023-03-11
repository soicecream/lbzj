package com.zime.ojdemo.modle.vo.result;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionStatisticsResult {
    // 最近七天日期格式 mm-dd,升序
    private List<String> dateStrList;

    // 最近七天每天AC数量
    private List<Long> acCountList;

    // 最近七天每天提交数量
    private List<Long> totalCountList;

}
