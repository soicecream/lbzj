package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
public interface ProblemService extends IService<Problem> {

    Problem getProblemById(int id);

    PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery);

    PageList getProList(long current, long limit, ProblemQuery problemQuery);

    Boolean addPro(Problem Problem) throws IOException;

    Boolean delPro(ArrayList<Integer> id);

    Boolean upPro(Problem problem) throws IOException;

    ArrayList<fileResult> getFile(Integer id);
}
