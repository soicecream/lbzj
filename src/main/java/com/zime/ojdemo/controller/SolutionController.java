package com.zime.ojdemo.controller;


import com.zime.ojdemo.entity.SolutionAndSourceCode;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.SolutionQuery;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * 提交管理接口
 * 2022年9月11日18:22:10
 */
@RestController
@RequestMapping("/submission")
@EnableTransactionManagement
public class SolutionController {

    @Autowired
    private SolutionService solutionService;


    /*
    * 提交列表接口
    * 2022年9月11日18:23:05
    * */
    @PostMapping("pageSubmissionsCondition/{current}/{limit}")
    public JsonResult<PageList> pageSolutionsCondition(@PathVariable long current, @PathVariable long limit,
                                                       @RequestBody(required = false) SolutionQuery SolutionQuery) {
        return JsonResult.success(solutionService.pageSolutionsCondition(current, limit, SolutionQuery));
    }

    /*
    * 上传提交接口
    * 2022年9月11日18:23:20
    * */
    @PostMapping("saveSubmission")
    public JsonResult<Boolean> saveSolution(HttpServletRequest request, @RequestBody(required = false) SolutionAndSourceCode solutionAndSourceCode) throws ParseException {
        return JsonResult.success(solutionService.save(request,solutionAndSourceCode));
    }

}

