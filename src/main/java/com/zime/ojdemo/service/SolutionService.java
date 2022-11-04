package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.SolutionAndSourceCode;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.SolutionQuery;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zime
 * @since 2021-10-01
 */
public interface SolutionService extends IService<Solution> {


    boolean save(HttpServletRequest request, SolutionAndSourceCode solutionAndSourceCode) throws ParseException;

    PageList pageSolutionsCondition(long current, long limit, SolutionQuery SolutionQuery);
}
