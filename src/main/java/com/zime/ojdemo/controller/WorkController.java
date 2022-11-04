package com.zime.ojdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.mapper.WorkMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.WorkListResult;
import com.zime.ojdemo.modle.vo.WorkRankresult;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    WorkService workService;

    @Autowired
    ProblemMapper problemMapper;

    @PostMapping("/pagework/{current}/{limit}")
    public JsonResult<PageList> pageWork(@PathVariable long current,@PathVariable long limit){
        Page<Work> pageProblem = new Page<>(current,limit);
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        workService.page(pageProblem,wrapper);
        long total = pageProblem.getTotal();
        List<Work> records = pageProblem.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return JsonResult.success(pageList);
    }

    @GetMapping("/pageworkid/{problemid}")
    public JsonResult<List<WorkListResult>> pageWorkid(@PathVariable String problemid){
     return JsonResult.success(workService.pageWorkid(problemid));
    }

    @GetMapping("/getworkrank/{workid}")
    public JsonResult<List<WorkRankresult>> getWork(@PathVariable Integer workid){
        System.out.println(workService.getworkrank(workid));
        return JsonResult.success(workService.getworkrank(workid));
    }

    @GetMapping("/getproblemid/{workid}")
    public JsonResult<String> getProblem(@PathVariable Integer workid){
        return JsonResult.success(workService.getProblem(workid));
    }

    @PostMapping("/addwork")
    public JsonResult<Boolean> addWork(@RequestBody Work work){
        return JsonResult.success(workService.save(work));
    }

    @PostMapping("/updatework")
    public JsonResult<Boolean> updatework(@RequestBody Work work){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workid",work.getWorkid());
        return JsonResult.success(workService.saveOrUpdate(work,wrapper));
    }

}
