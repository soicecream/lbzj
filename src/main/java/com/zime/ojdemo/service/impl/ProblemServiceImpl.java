package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.*;
import com.zime.ojdemo.entity.Dto.AdminProblemDto;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.result.ProblemListResult;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.untils.Io;
import io.netty.util.CharsetUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

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

    @Autowired
    private ProblemCaseServiceImpl problemCaseService;


    private String samplesPathUrl = "D:\\毕设\\lbzj\\samples\\";


    @Override
    public ProblemDto getProblem(int id) {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setProblem(getProblemById(id));
        problemDto.setTagsList(getTagsById(id));
        return problemDto;
    }

    @Override
    public AdminProblemDto AdminGetProblem(int id) throws IOException {
        ProblemDto problemDto = getProblem(id);
        AdminProblemDto adminProblemDto = new AdminProblemDto();
        BeanUtils.copyProperties(problemDto, adminProblemDto); // problemDto 的已有的值 赋值给 adminProblemDto 中
        adminProblemDto.setSamples(problemCaseService.GetProblemCase(id));
        return adminProblemDto;
    }

    public ArrayList<fileResult> getFile(Integer id) {
        String wz = "D:\\home\\judge\\data\\" + id;
        File file = new File(wz);
        return Io.readPro(file);
    }


    //    获取后台测试数据
    public List<ProblemCase> getSample(Integer id) throws IOException {
        String sampleFileName = samplesPathUrl + "\\" + String.format("%05d", id);
        File problemSample = new File(sampleFileName);
        File files[] = problemSample.listFiles();
        TreeMap<String, ProblemCase> treeMap = new TreeMap<>();
        for (File file : files) {
            String s[] = file.getName().split("\\.");

            ProblemCase problemCase;
            if (treeMap.containsKey(s[0])) {
                problemCase = treeMap.get(s[0]);
            } else {
                problemCase = new ProblemCase();
            }

            String FileName = sampleFileName + "\\" + file.getName();
            if (s[1].equals("in")) {
                problemCase.setInput(readSampleFile(FileName));
            } else {
                problemCase.setOutput(readSampleFile(FileName));
            }

            treeMap.put(s[0], problemCase);
        }

        List<ProblemCase> list = new ArrayList<>();
        for (String i : treeMap.keySet()) {
            list.add(treeMap.get(i));
        }
        return list;
    }


    @Override
    public PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery) {
        Page<Problem> pageProblem = new Page<>(current, limit);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        Integer degree = problemQuery.getDegree();
        String id = problemQuery.getId();

        //判断条件值是否为空，如果不为空拼接条件
//        wrapper.orderByAsc("in_date");
        wrapper.orderByDesc("id");
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
//        wrapper.orderByAsc("in_date");
//        wrapper.orderByAsc("problem_id");
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
    public Boolean CreateOrUpdate(AdminProblemDto problemDto) throws IOException {
        Problem problem = problemDto.getProblem();

//        存问题
        boolean addProblem = createOrUpdateProblem(problem);

        Integer problemId = getOne(new QueryWrapper<Problem>().eq("title", problem.getTitle())).getProblemId();

//        存标签
        boolean addTags = createOrUpdateTags(problemDto.getTagsList(), problemId);

//        存后台测试样例
        boolean addSample = createOrUpdateSample(problemDto.getSamples(), problemId);

        return addProblem && addTags && addSample;
    }

    public Boolean delPro(ArrayList<Integer> ids) {
        boolean f = true;
        for (int i : ids) {
            String sampleFileName = samplesPathUrl + "" + String.format("%05d", i);
            File problemSampleFile = new File(sampleFileName);
//          判断文件夹是否存在 存在则删除
            if (problemSampleFile.isDirectory()) {
                deleteFile(problemSampleFile);
            }
        }
        return removeByIds(ids);
    }


//    ----------------------------------------------------------------------------------------------------------------------------------

    //    存问题
    public Boolean createOrUpdateProblem(Problem problem) {
        List<Problem> problemList = getProblemListByIds(problem.getProblemId());
        if (problemList.size() == 0) {
            return save(problem);
        } else {
            QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
            problemQueryWrapper.eq("problem_id", problem.getProblemId());
            return update(problem, problemQueryWrapper);
        }
    }

    //    存标签
    public Boolean createOrUpdateTags(List<Tags> tagsList, Integer problemId) {
        System.err.println("tagslist\t" + tagsList);
        if (tagsList != null) {
            // 处理
            List<Integer> list = new ArrayList<>();
            for (Tags tags : tagsList) {
                if (tags.getId() == null) {
                    tagService.CreateOrUpdate(tags);

                    tags = tagService.getOne(new QueryWrapper<Tags>().eq("value", tags.getValue()));
                }

                list.add(tags.getId());
            }

            // 导入
            return problemTagsService.CreateOrUpdate(list, problemId);
        }
        return true;
    }

    // 存后台测试数据
    public Boolean createOrUpdateSample(List<ProblemCase> problemCaseList, Integer problemId) throws IOException {
        // 删除
        String sampleFileName = samplesPathUrl + "" + String.format("%05d", problemId);
        File problemSampleFile = new File(sampleFileName);
//        判断文件夹是否存在 存在则删除
        if (problemSampleFile.isDirectory()) {
            deleteFile(problemSampleFile);
        }
        problemSampleFile.mkdirs();

        // 导入
        init_ProblemSampleCase(problemCaseList, problemId);

        for (ProblemCase i : problemCaseList) {
            i.setProblemId(problemId);
        }
        // 存数据库中
        return problemCaseService.createOrUpdate(problemCaseList, problemId);
    }


//    ----------------------------------------------------------------------------------------------------------------------------------

    //    id查询问题
    public Problem getProblemById(int id) {
        return getOne(new QueryWrapper<Problem>().eq("problem_id", id));
    }

    //    id查询标签
    public List<Tags> getTagsById(int id) {
        return problemTagsService.getProblemTags(id);
    }

    //    根据id查询所有问题
    public List<Problem> getProblemListByIds(Integer problemId) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemId);
        return list(queryWrapper);
    }

    //    根据标题查询所有问题
    public List<Problem> getProblemListByTitles(String title) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return list(queryWrapper);
    }


    //    删除文件
    public boolean deleteFile(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f); // 是文件夹的话 去删除其中的文件及文件夹
            } else {
                f.delete(); // 文件则直接删除
            }
        }
        file.delete(); // 删除最后的文件夹
        return true;
    }

    //    导入问题后台测试数据
    public void init_ProblemSampleCase(List<ProblemCase> samples, Integer problemId) throws IOException {
        String sampleFileName = samplesPathUrl + "" + String.format("%05d", problemId);
        for (int i = 0; i < samples.size(); i++) {
            String input = samples.get(i).getInput().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
            String output = samples.get(i).getOutput().replaceAll("\r\n", "\n").replaceAll("\r", "\n");

            createSampleFile(sampleFileName + "/" + String.format("test%02d", i + 1) + ".in", input);
            createSampleFile(sampleFileName + "/" + String.format("test%02d", i + 1) + ".out", output);
        }
    }

    //    创建文件
    public void createSampleFile(String FileUrlName, String Sample) throws IOException {
        File SampleFile = new File(FileUrlName);
        SampleFile.createNewFile();

        FileWriter SampleFileWriter = new FileWriter(SampleFile);
        SampleFileWriter.write(Sample);
        SampleFileWriter.flush();
        SampleFileWriter.close();
    }

    //    获取测试样例文件的内容
    public String readSampleFile(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        for (String i : list) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

}
