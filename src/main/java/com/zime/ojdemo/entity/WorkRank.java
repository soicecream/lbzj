package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("workrank")
public class WorkRank {
    private Integer workid;
    private Integer ranking;
    private String username;
    private Integer intrgeral;
    private String probem;
}
