package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.ProblemCase;
import com.zime.ojdemo.mapper.ProblemCaseMapper;
import com.zime.ojdemo.service.IProblemCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-02-07
 */
@Service
public class ProblemCaseServiceImpl extends ServiceImpl<ProblemCaseMapper, ProblemCase> implements IProblemCaseService {

    public List<ProblemCase> GetProblemCase(Integer problemId) {
        return list(new QueryWrapper<ProblemCase>().eq("problem_id", problemId));
    }

    public Boolean createOrUpdate(List<ProblemCase> problemCase, Integer problemId) {
        remove(new QueryWrapper<ProblemCase>().eq("problem_id", problemId));

        return saveBatch(problemCase);
    }
}
