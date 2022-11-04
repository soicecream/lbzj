package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.modle.vo.AddProblem;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.ProblemQuery;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;


/**
 * 问题管理接口
 * 2022年9月11日18:20:14
 */
@RestController
@RequestMapping("/problem")
@CrossOrigin
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /*
    * 根据id获取问题接口
    * 2022年9月11日18:20:42
    * */
    @GetMapping("getProblemById/{id}")
    public JsonResult<Problem> getProblemById(@PathVariable int id) {
        return JsonResult.success(problemService.getProblemById(id));
    }

    /*
    * 问题列表接口
    * 2022年9月11日18:21:12
    * */
    @PostMapping("pageProblemsCondition/{current}/{limit}")
    public JsonResult<PageList> pageProblemsCondition(@PathVariable long current, @PathVariable long limit,
                                                      @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.pageProblemsCondition(current,limit,problemQuery));
    }

    @PostMapping("adminprolist/{current}/{limit}")
    public JsonResult<PageList> pageAdminProlist(@PathVariable long current, @PathVariable long limit,
                                                      @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.getProList(current,limit,problemQuery));
    }

    @GetMapping("getFile/{id}")
    public JsonResult<ArrayList<fileResult>> udpatePro(@PathVariable Integer id)  {
        return JsonResult.success(problemService.getFile(id));
    }

    @PostMapping("updatePro")
    public JsonResult<Boolean> udpatePro(@RequestBody Problem problem) throws IOException {
        return JsonResult.success(problemService.upPro(problem));
    }

    @PostMapping("addPro")
    public JsonResult<Boolean> addPro(@RequestBody Problem problem) throws IOException {
            return JsonResult.success(problemService.addPro(problem));
    }

    @PostMapping("delPro")
    public JsonResult<Boolean> delPro(@RequestBody ArrayList<Integer> ids){
        return JsonResult.success(problemService.delPro(ids));
    }


}

