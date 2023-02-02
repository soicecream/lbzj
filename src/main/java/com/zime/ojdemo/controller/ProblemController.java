package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    @GetMapping("getPros")
    public JsonResult<List<Problem>> getProblems() {
        return JsonResult.success(problemService.list());
    }
    /*
     * 问题列表接口
     * 2022年9月11日18:21:12
     * */
    @PostMapping("pageProblemsCondition/{current}/{limit}")
    public JsonResult<PageList> pageProblemsCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.pageProblemsCondition(current, limit, problemQuery));
    }

    @PostMapping("adminprolist/{current}/{limit}")
    public JsonResult<PageList> pageAdminProlist(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.getProList(current, limit, problemQuery));
    }

    @GetMapping("getFile/{id}")
    public JsonResult<ArrayList<fileResult>> udpatePro(@PathVariable Integer id) {
        return JsonResult.success(problemService.getFile(id));
    }

    @GetMapping("getSample/{id}")
    public JsonResult getSample(@PathVariable Integer id) throws IOException {
        return JsonResult.success(problemService.getSample(id));
    }

    @PostMapping("")
    public JsonResult<Boolean> CreateOrUpdate(@RequestBody ProblemDto problemDto) throws IOException {
        Problem problem = problemDto.getProblem();
        if (problem.getProblemId() == null) {
            List<Problem> problemList = problemService.getProblemListByTitles(problem.getTitle());
            if (problemList.size() != 0) {
                return JsonResult.error("标题重复，请重新输入");
            }
        }
        if (problemDto.getSamples().size() == 0) {
            return JsonResult.error("后台测试样例不能为空");
        }
        return JsonResult.success(problemService.CreateOrUpdate(problemDto));
    }

    @PostMapping("delPro")
    public JsonResult<Boolean> delPro(@RequestBody ArrayList<Integer> ids) {
        return JsonResult.success(problemService.delPro(ids));
    }


}

