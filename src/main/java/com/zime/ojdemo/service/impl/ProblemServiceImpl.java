package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.modle.vo.AddProblem;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.ProblemListResult;
import com.zime.ojdemo.modle.vo.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.untils.Io;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Autowired
    SolutionService solutionService;

    @Override
    public Problem getProblemById(int id){
        return getById(id);
    }

    public ArrayList<fileResult> getFile(Integer id){
        String wz="C:\\Users\\26444\\Desktop\\data\\"+id;
        File file=new File(wz);
        return Io.readPro(file);
    }

    @Override
    public PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery) {
        Page<Problem> pageProblem = new Page<>(current,limit);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        Integer degree = problemQuery.getDegree();
        String id = problemQuery.getId();

        //判断条件值是否为空，如果不为空拼接条件
        wrapper.orderByAsc("in_date");
        if(degree != null) {
            wrapper.eq("degree", degree);
        }
        if(!StringUtils.isEmpty(id))  {
            wrapper.eq("title", id).or().eq("problem_id", id);
        }
        page(pageProblem, wrapper);
        long total = pageProblem.getTotal();
        List<Problem> records = pageProblem.getRecords();

        List<ProblemListResult> results = new LinkedList<>();
        for(Problem a:records){
            ProblemListResult problemListResult = new ProblemListResult();
            problemListResult.setProblemId(a.getProblemId());
            problemListResult.setAccepted(a.getAccepted());
//            problemListResult.setDegree(a.getDegree());
//            problemListResult.setOrdernum(a.getOrdernum());
            problemListResult.setSubmit(a.getSubmit());
            problemListResult.setSolved(a.getSolved());
            problemListResult.setTitle(a.getTitle());
            problemListResult.setDefunct(a.getDefunct());
            problemListResult.setState(0);
            QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("problem_id",a.getProblemId());
            problemListResult.setSubmit((int) solutionService.count(queryWrapper));

            queryWrapper.eq("user_id",problemQuery.getUserId());
            queryWrapper.last("limit 1");
            Solution solution =  solutionService.getOne(queryWrapper);
            if(solution!=null)
                problemListResult.setState(-1);
            queryWrapper.eq("result",4);
            solution =  solutionService.getOne(queryWrapper);
            if(solution!=null)
                problemListResult.setState(1);
            results.add(problemListResult);
        }

        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(results);
        return pageList;
    }

    @Override
    public PageList getProList(long current, long limit, ProblemQuery problemQuery) {
        Page<Problem> pageProblem = new Page<>(current,limit);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        Integer degree = problemQuery.getDegree();
        String id = problemQuery.getId();

        //判断条件值是否为空，如果不为空拼接条件
        wrapper.orderByAsc("in_date");
        if(degree != null) {
            wrapper.eq("degree", degree);
        }
        page(pageProblem, wrapper);
        long total = pageProblem.getTotal();
        List<Problem> records = pageProblem.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
    }

    public Boolean addPro(Problem Problem) throws IOException {
        save(Problem);
        String wz="C:\\Users\\26444\\Desktop\\data\\"+Problem.getProblemId();
        File file=new File(wz);
        file.mkdir();
        Io.write(Problem.getSampleInput(),wz+"\\test.in");
        Io.write(Problem.getSampleOutput(),wz+"\\test.out");
        return true;
    }

    public Boolean delPro(ArrayList<Integer> ids){
        for(Integer id:ids){
            File file = new File("C:/Users/26444/Desktop/data/"+id);
            if(!Io.deleteFile(file)) return false;
        }
        return removeByIds(ids);
    }

    public Boolean upPro(Problem problem) throws IOException {
        QueryWrapper<Problem> wrapper=new QueryWrapper<>();
        wrapper.eq("problem_id",problem.getProblemId());
        String wz="C:\\Users\\26444\\Desktop\\data\\"+problem.getProblemId();
        File file=new File(wz);
        Io.write(problem.getSampleInput(),wz+"\\test.in");
        Io.write(problem.getSampleOutput(),wz+"\\test.out");
        return    update(problem,wrapper);
    }
}
