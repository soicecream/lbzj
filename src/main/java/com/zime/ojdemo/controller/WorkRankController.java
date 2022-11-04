package com.zime.ojdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.entity.WorkRank;
import com.zime.ojdemo.mapper.WorkRankMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.WorkRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workrank")
public class WorkRankController {

    @Autowired
    WorkRankMapper workRankMapper;

    @Autowired
    WorkRankService workRankService;

    @GetMapping("/pageworkrank/{workid}")
    public JsonResult<List<WorkRank>> pageWork(@PathVariable long workid){
        QueryWrapper<WorkRank> wrapper = new QueryWrapper<>();
        wrapper.eq("workid",workid);
        return JsonResult.success(workRankService.list(wrapper));
    }


}
