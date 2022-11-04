package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.modle.vo.WorkListResult;
import com.zime.ojdemo.modle.vo.WorkRankresult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface WorkService extends IService<Work> {
    ArrayList<WorkRankresult> getworkrank(Integer workid);
    String getProblem(Integer workid);
    List<WorkListResult> pageWorkid(String problemid);
}
