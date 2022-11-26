package com.zime.ojdemo.modle.vo.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkRankresult {
    public String username;
    private Integer times;
    private Integer pros;
    private ArrayList<Integer> time;
    private ArrayList<Integer> count;

    public WorkRankresult(String username,Integer size){
     this.username=username;
     this.time=new ArrayList<>(size);
     this.count=new ArrayList<>(size);
     this.pros=0;
     this.times=0;
     for (int i=0;i<size;i++){
       this.time.add(-1);
       this.count.add(0);
     }
    }

}
