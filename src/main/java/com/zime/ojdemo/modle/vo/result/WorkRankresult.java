package com.zime.ojdemo.modle.vo.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkRankresult {
    public String username;
    private Integer times;
    private ArrayList<Integer> time;
    private ArrayList<Integer> count;

}
