package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.WorkQuery;
import com.zime.ojdemo.modle.vo.result.WorkListResult;
import com.zime.ojdemo.modle.vo.result.WorkRankresult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public interface WorkService extends IService<Work> {

    ArrayList<WorkRankresult> getworkrank(Integer workid);

    String getProblem(Integer workid);

    List<WorkListResult> pageWorkid(String problemid);

    Work getWork(Integer id);

    PageList pageWork(long current, long limit, WorkQuery workQuery,Boolean pd);
}
