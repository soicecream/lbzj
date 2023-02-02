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
import com.zime.ojdemo.untils.SecurityUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return JsonResult.success(workService.pageWork(current,limit,workQuery,true));
    }

    /*
   后台竞赛列表接口
   */
    @PreAuthorize("@ss.hasPermi('work:list')")
    @PostMapping("/adminpagework/{current}/{limit}")
    public JsonResult<PageList> adminPageWork(@PathVariable long current, @PathVariable long limit, @RequestBody WorkQuery workQuery){
        return JsonResult.success(workService.pageWork(current,limit,workQuery,false));
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


    @PreAuthorize("@ss.hasPermi('user:add')")
    @PostMapping("/addwork")
    public JsonResult<Boolean> addWork(@RequestBody Work work){
        work.setFounder(String.valueOf(SecurityUntils.getLoginUser().getUserId()));
        work.setOpen(true);
        return JsonResult.success(workService.save(work));
    }

    @PreAuthorize("@ss.hasPermi('work:up')")
    @PostMapping("/updatework")
    public JsonResult<Boolean> updatework(@RequestBody Work work){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workid",work.getWorkid());
        return JsonResult.success(workService.saveOrUpdate(work,wrapper));
    }

    @PreAuthorize("@ss.hasPermi('work:del')")
    @PostMapping("/delwork")
    public JsonResult<Boolean> delPro(@RequestBody ArrayList<Integer> ids){
        return JsonResult.success(workService.removeByIds(ids));
    }
}
