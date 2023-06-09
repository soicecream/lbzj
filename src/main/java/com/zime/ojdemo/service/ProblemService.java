package com.zime.ojdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.controller.ProblemController;
import com.zime.ojdemo.entity.Dto.AdminProblemDto;
import com.zime.ojdemo.entity.Dto.ProblemDto;
import com.zime.ojdemo.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.entity.ProblemCase;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.result.fileResult;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    ProblemDto getProblem(int id);

    AdminProblemDto AdminGetProblem(int id) throws IOException;

    PageList pageProblemsCondition(long current, long limit, ProblemQuery problemQuery);

    Page<Problem> adminGetProList(long current, long limit, ProblemQuery problemQuery);

    Boolean CreateOrUpdate(AdminProblemDto problem) throws IOException;

    Boolean delPro(ArrayList<Integer> id);

    List<Problem> getProblemListByIds(Integer id);

    List<Problem> getProblemListByTitles(String title);

    ArrayList<fileResult> getFile(Integer id);

    List<ProblemCase> getSample(Integer id) throws IOException;


    JsonResult uploadSampleFile(MultipartFile file) throws IOException;

    void downloadSample(Integer id, HttpServletResponse response) throws IOException;


    JsonResult uploadVideo(Integer problemId, MultipartFile file) throws IOException;

    void getVideo(Integer problemId, HttpServletResponse response) throws IOException;

    void adminGetVideo(Integer problemId, HttpServletResponse response) throws IOException;


    JsonResult deleteVideo(Integer problemId);

}
