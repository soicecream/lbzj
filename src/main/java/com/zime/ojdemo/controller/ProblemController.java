package com.zime.ojdemo.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zime.ojdemo.entity.Dto.AdminProblemDto;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import com.zime.ojdemo.service.ProblemService;
import com.zime.ojdemo.service.impl.ProblemServiceImpl;
import com.zime.ojdemo.service.impl.ProblemTagsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 问题管理接口
 * 2022年9月11日18:20:14
 */
@RestController
@RequestMapping("/problem")
@CrossOrigin
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemServiceImpl IProblemService;

    @Autowired
    private ProblemTagsServiceImpl problemTagsService;

    @GetMapping("video")
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String videoPath = "D:\\毕设\\lbzj\\vedio\\" + "problem-vedio-" + String.format("%05d", 1000) + ".mp4";
        System.err.println(videoPath);

        // 设置响应头
        response.setContentType("video/mp4");
        response.setHeader("Content-Disposition", "inline; filename=video.mp4");

        // 读取视频文件并输出
        FileInputStream in = new FileInputStream(new File(videoPath));
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
    }


    /*
     * 根据id获取问题接口
     * 2022年9月11日18:20:42
     * */
    @GetMapping("getProblemById/{id}")
    public JsonResult<ProblemDto> getProblemById(@PathVariable int id) {
        return JsonResult.success(problemService.getProblem(id));
    }

    @GetMapping("admin/getProblemById/{id}")
    public JsonResult<AdminProblemDto> AdminGetProblemById(@PathVariable int id) throws IOException {
        return JsonResult.success(problemService.AdminGetProblem(id));
    }

    @GetMapping("getPros")
    public JsonResult<List<Problem>> getProblems() {
        return JsonResult.success(problemService.list());
    }

    /*
     * 问题列表接口
     * 2022年9月11日18:21:12
     * */
    @PostMapping("pageProblemsCondition/{current}/{limit}")
    public JsonResult<PageList> pageProblemsCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.pageProblemsCondition(current, limit, problemQuery));
    }

    @PostMapping("adminprolist/{current}/{limit}")
    public JsonResult pageAdminProlist(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ProblemQuery problemQuery) {
        return JsonResult.success(problemService.adminGetProList(current, limit, problemQuery));
    }

    @GetMapping("getFile/{id}")
    public JsonResult<ArrayList<fileResult>> udpatePro(@PathVariable Integer id) {
        return JsonResult.success(problemService.getFile(id));
    }

    @GetMapping("getSample/{id}")
    public JsonResult getSample(@PathVariable Integer id) throws IOException {
        return JsonResult.success(problemService.getSample(id));
    }

    @PostMapping("chang/enable")
    public JsonResult changEnable(@RequestBody Problem problem) {
        return JsonResult.success(problemService.updateById(problem));
    }

    @PostMapping("")
    public JsonResult CreateOrUpdate(@RequestBody AdminProblemDto problemDto) throws IOException {
        Problem problem = problemDto.getProblem();
        if (problem.getProblemId() == null) {
            List<Problem> problemList = problemService.getProblemListByTitles(problem.getTitle());
            if (problemList.size() != 0) {
                return JsonResult.error("标题重复，请重新输入");
            }
        }
        if (problemDto.getSamples().size() == 0) {
            return JsonResult.error("后台测试样例不能为空");
        }
        return JsonResult.success(problemService.CreateOrUpdate(problemDto));
    }


    @PostMapping("upload/sample")
    public JsonResult uploadProblemSample(MultipartFile file) throws IOException {
        return problemService.uploadSampleFile(file);
    }

    @GetMapping("download/sample/{id}")
    public void downloadSample(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        problemService.downloadSample(id, response);
    }


    @PostMapping("upload/video/{problemId}")
    public JsonResult uploadVideo(@PathVariable Integer problemId, MultipartFile file) throws IOException {
        return problemService.uploadVideo(problemId, file);
    }


    @JsonIgnore
    @PostMapping("get/video/{problemId}")
    public JsonResult getVideo(@PathVariable Integer problemId, HttpServletResponse response) throws IOException {
        return problemService.getVideo(problemId, response);
    }

    @GetMapping("admin/get/video/{problemId}")
    public JsonResult adminGetVideo(@PathVariable Integer problemId, HttpServletResponse response) throws IOException {
        return problemService.adminGetVideo(problemId, response);
    }


    @PostMapping("delete/video/{problemId}")
    public JsonResult deleteVideo(@PathVariable Integer problemId) {
        return problemService.deleteVideo(problemId);
    }


    @PostMapping("delPro")
    public JsonResult<Boolean> delPro(@RequestBody ArrayList<Integer> ids) {
        return JsonResult.success(problemService.delPro(ids));
    }


}

