package com.zime.ojdemo.service;

import com.zime.ojdemo.controller.ProblemController;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.ProblemCase;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import org.springframework.security.core.parameters.P;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
public interface ProblemService extends IService<Problem> {

    Problem getProblemById(int id);

    PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery);

    PageList getProList(long current, long limit, ProblemQuery problemQuery);

    Boolean CreateOrUpdate(ProblemDto problem) throws IOException;

    Boolean delPro(ArrayList<Integer> id);

    List<Problem> getProblemListByIds(Integer id);

    List<Problem> getProblemListByTitles(String title);

    ArrayList<fileResult> getFile(Integer id);

    List<ProblemCase> getSample(Integer id) throws IOException;
}
