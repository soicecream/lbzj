package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.ProblemTags;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Tags;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.result.ProblemListResult;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.untils.Io;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Autowired
    SolutionService solutionService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private ProblemTagsServiceImpl problemTagsService;



    @Override
    public Problem getProblemById(int id) {
        return getById(id);
    }

    public ArrayList<fileResult> getFile(Integer id) {
        String wz = "D:\\home\\judge\\data\\" + id;
        File file = new File(wz);
        return Io.readPro(file);
    }

    @Override
    public PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery) {
        Page<Problem> pageProblem = new Page<>(current, limit);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        Integer degree = problemQuery.getDegree();
        String id = problemQuery.getId();

        //判断条件值是否为空，如果不为空拼接条件
        wrapper.orderByAsc("in_date");
        if (degree != null) {
            wrapper.eq("degree", degree);
        }
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("title", id).or().eq("problem_id", id);
        }
        page(pageProblem, wrapper);
        long total = pageProblem.getTotal();
        List<Problem> records = pageProblem.getRecords();

        List<ProblemListResult> results = new LinkedList<>();
        for (Problem a : records) {
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
            queryWrapper.eq("problem_id", a.getProblemId());
            problemListResult.setSubmit((int) solutionService.count(queryWrapper));

            queryWrapper.eq("user_id", problemQuery.getUserId());
            queryWrapper.last("limit 1");
            Solution solution = solutionService.getOne(queryWrapper);
            if (solution != null) problemListResult.setState(-1);
            queryWrapper.eq("result", 4);
            solution = solutionService.getOne(queryWrapper);
            if (solution != null) problemListResult.setState(1);
            results.add(problemListResult);
        }

        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(results);
        return pageList;
    }

    @Override
    public PageList getProList(long current, long limit, ProblemQuery problemQuery) {
        Page<Problem> pageProblem = new Page<>(current, limit);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        Integer degree = problemQuery.getDegree();
        String id = problemQuery.getId();

        //判断条件值是否为空，如果不为空拼接条件
        wrapper.orderByAsc("in_date");
        if (degree != null) {
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

    //    先存问题 再存标签 最后存后台测试样例
    public Boolean CreateOrUpdate(ProblemDto problemDto) {
        Problem problem = problemDto.getProblem();
        List<Problem> problemList = getProblemListByIds(problem.getProblemId());

//        存问题
        boolean addProblem = true;
        if (problemList.size() == 0) {
            addProblem = save(problem);
        } else {
            QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
            problemQueryWrapper.eq("problem_id", problem.getProblemId());
            addProblem = update(problem, problemQueryWrapper);
        }
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("title", problem.getTitle());
        problem = getOne(problemQueryWrapper);

//        存标签
        boolean addTags = true;
        if (problemDto.getTagsList() != null) {
            List<Integer> TagsList = new ArrayList<>();
            for (Tags tags : problemDto.getTagsList()) {
                if (tags.getId() == null) {
                    tagService.CreateOrUpdate(tags);

                    QueryWrapper<Tags> tagsQueryWrapper = new QueryWrapper<>();
                    tagsQueryWrapper.eq("value", tags.getValue());
                    tags = tagService.getOne(tagsQueryWrapper);
                }

                TagsList.add(tags.getId());
            }
            addTags = problemTagsService.CreateOrUpdate(problem.getProblemId(), TagsList);
        }

        boolean addSample = true;


        return addProblem && addTags;
    }

    public Boolean delPro(ArrayList<Integer> ids) {
//        for (Integer id : ids) {
//            File file = new File("C:/Users/26444/Desktop/data/" + id);
//            if (!Io.deleteFile(file)) return false;
//        }
//        System.out.println("----------" + ids);
        return removeByIds(ids);
    }

    public List<Problem> getProblemListByIds(Integer problemId) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemId);
        return list(queryWrapper);
    }

    public List<Problem> getProblemListByTitles(String title) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return list(queryWrapper);
    }

//    public Boolean upPro(Problem problem) throws IOException {
//        QueryWrapper<Problem> wrapper = new QueryWrapper<>();
//        wrapper.eq("problem_id", problem.getProblemId());
//        String wz = "C:\\Users\\26444\\Desktop\\data\\" + problem.getProblemId();
//        File file = new File(wz);
//        Io.write(problem.getSampleInput(), wz + "\\test.in");
//        Io.write(problem.getSampleOutput(), wz + "\\test.out");
//        return update(problem, wrapper);
//    }
}
