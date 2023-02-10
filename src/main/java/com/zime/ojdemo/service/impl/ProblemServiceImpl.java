package com.zime.ojdemo.service.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.*;
import com.zime.ojdemo.entity.Dto.AdminProblemDto;
import com.zime.ojdemo.entity.Dto.ProblemCaseDto;
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
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        wrapper.orderByAsc("problem_id");
        if (degree != null) {
            wrapper.eq("degree", degree);
        }
        if (problemQuery.getTitle() != null) {
            wrapper.like("title", problemQuery.getTitle());
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
        boolean addSample = true;
        if (problemDto.getIsUploadCase()) {

        } else {
            addSample = createOrUpdateSample(problemDto.getSamples(), problemId);
        }

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

    //    上传后台测试样例
    @Override
    public JsonResult uploadSampleFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String type = FileUtil.extName(originalFileName);
        if (type == null) {
            return JsonResult.error(400, "文件类型有问题，请确认文件类型");
        } else if (!type.equals("zip")) {
            return JsonResult.error(400, "文件类型请选择zip");
        }
        long size = file.getSize();
        if (size == 0) {
            return JsonResult.error(400, "暂无文件，请选择文件");
        }


//        文件夹
        String fileDir = samplesPathUrl + "/" + "uploadFileCache";
//        zip解压路劲
        String filePath = fileDir + "/" + file.getOriginalFilename();

//        文件夹若不存在则新建
        FileUtil.mkdir(fileDir);

//        上传
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            return JsonResult.error(400, "服务器异常：评测数据上传失败！");
        }

//        解压到指定文件夹下
        ZipUtil.unzip(filePath, fileDir);
//        删除zip
        FileUtil.del(filePath);

//        检查文件是否为空
        File textCaseFileList = new File(fileDir);
        File[] files = textCaseFileList.listFiles();
        if (files == null || files.length == 0) {
            FileUtil.del(fileDir);
            return JsonResult.error("评测数据压缩包里文件不能为空");
        }


        TreeMap<String, ProblemCaseDto> treeMap = new TreeMap<>();
        for (File i : files) {
            String Name = i.getName();
            String name = Name.substring(0, Name.lastIndexOf("."));
            String suffix = FileUtil.extName(Name);

            treeMap.putIfAbsent(name, new ProblemCaseDto());
            if (suffix.equals("in")) {
                treeMap.get(name).setInputName(Name);
                treeMap.get(name).setInput(readSampleFile(i.toPath().toString()));
            } else if (suffix.equals("out")) {
                treeMap.get(name).setOutputName(Name);
                treeMap.get(name).setOutput(readSampleFile(i.toPath().toString()));
            } else {
                return JsonResult.error(400, "zip中的数据格式有误, 请确认是否为 *.in, *.out");
            }
        }

        List<ProblemCaseDto> problemCaseDtoList = new ArrayList<>();
        for (String i : treeMap.keySet()) {
            problemCaseDtoList.add(treeMap.get(i));
        }

//        用完就丢
        FileUtil.del(fileDir);

        return JsonResult.success(problemCaseDtoList);
    }

    //    下载后台测试样例
    @Override
    public void downloadSample(Integer problemId, HttpServletResponse response) throws IOException {
        String fileDir = samplesPathUrl + "/" + String.format("%05d", problemId);
        File file = new File(fileDir);

//        本地为空 去数据库找
        if (!file.exists()) {
            List<ProblemCase> list = problemCaseService.list(new QueryWrapper<ProblemCase>().eq("problem_id", problemId));

            if (CollectionUtils.isEmpty(list)) {
                JsonResult.error(400, "该题目的后台测试数据为空");
            }

//            将数据库中的数据 导入到服务器中
            init_ProblemSampleCase(list, problemId);
        }

        System.err.println("----------------------------------------------------------- start");

        String fileName = "problem-" + String.format("%05d", problemId) + "-testCase" + ".zip";
        String zipFileName = samplesPathUrl + "/" + fileName;
//        将对应的文件夹的文件压缩成.zip
        ZipUtil.zip(fileDir, zipFileName);

        File zipFile = new File(zipFileName);

        System.err.println("----------------------------------------------------------- toZipOk");

//        设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        任意类型的二进制流数据
        response.setContentType("application/octet-stream");

//        读取文件的字节流
        os.write(FileUtil.readBytes(zipFile));
        os.flush();
        os.close();

        System.err.println("----------------------------------------------------------- end");

//        删除临时文件
//        FileUtil.del(zipFile);
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
        String sampleFileName = samplesPathUrl + "/" + String.format("%05d", problemId);
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
