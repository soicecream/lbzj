package com.zime.ojdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.WorkQuery;
import com.zime.ojdemo.modle.vo.result.WorkListResult;
import com.zime.ojdemo.modle.vo.result.WorkRankresult;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    WorkService workService;

    @Autowired
    ProblemMapper problemMapper;

    /*
    竞赛列表接口
     */
    @PostMapping("/pagework/{current}/{limit}")
    public JsonResult<PageList> pageWork(@PathVariable long current, @PathVariable long limit, @RequestBody WorkQuery workQuery){
        Page<Work> pageProblem = new Page<>(current,limit);
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        if(workQuery.getWorkname()!=null&&!workQuery.getWorkname().equals("")) wrapper.like("name",workQuery.getWorkname());
        wrapper.orderByDesc("endtime");
        workService.page(pageProblem,wrapper);
        long total = pageProblem.getTotal();
        List<Work> records = pageProblem.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return JsonResult.success(pageList);
    }

    /*
    竞赛接口
     */
    @GetMapping("/getwork/{id}")
    public JsonResult<Work> pageWorkid(@PathVariable Integer id){
        return JsonResult.success(workService.getWork(id));
    }

    /*
    竞赛题目接口
     */
    @GetMapping("/pageworkid/{problemid}")
    public JsonResult<List<WorkListResult>> pageWorkid(@PathVariable String problemid){
     return JsonResult.success(workService.pageWorkid(problemid));
    }

    /*
    竞赛排名接口
     */
    @GetMapping("/getworkrank/{workid}")
    public JsonResult<ArrayList<WorkRankresult>> getWorkrank(@PathVariable Integer workid){
        return JsonResult.success(workService.getworkrank(workid));
    }

    /*
    竞赛题目接口
    题目按&分割
     */
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
    @PostMapping("/delwork")
    public JsonResult<Boolean> delPro(@RequestBody ArrayList<Integer> ids){
        return JsonResult.success(workService.removeByIds(ids));
    }
}
