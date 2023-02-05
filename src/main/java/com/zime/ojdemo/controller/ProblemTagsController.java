package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.ProblemTags;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.IProblemTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-12-21
 */
@RestController
@RequestMapping("/problem/tags")
public class ProblemTagsController {

    @Autowired
    private IProblemTagsService problemTagsService;

    @GetMapping("/getProblem/{problemId}")
    private JsonResult findProblem(@PathVariable Integer problemId) {
        return JsonResult.success(problemTagsService.getProblemTags(problemId));
    }

    @PostMapping("/setOrUpdateTags/problemId")
    private JsonResult CreateOrUpdate(@PathVariable Integer problemId, @RequestBody List<Integer> tags) {
        return JsonResult.success(problemTagsService.CreateOrUpdate(problemId, tags));
    }

}

