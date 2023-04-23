package com.zime.ojdemo.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.SolutionAndSourceCode;
import com.zime.ojdemo.entity.SourceCode;
import com.zime.ojdemo.mapper.SolutionMapper;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.SolutionQuery;
import com.zime.ojdemo.modle.vo.result.SubmissionStatisticsResult;
import com.zime.ojdemo.service.SolutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.SourceCodeService;
import com.zime.ojdemo.untils.Constants;
import com.zime.ojdemo.untils.SecurityUntils;
import com.zime.ojdemo.untils.TokenServie;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zime
 * @since 2021-10-01
 */
@Service
@EnableTransactionManagement//事务注解
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements SolutionService {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    TokenServie tokenServie;


    /*
        同时向两张表中插入数据时，如果需要保证要么都插入要么都不插入，可以使用事务。事务注解仅在含接口的服务类中可以被使用，且public
        在事务注解的函数中，可以获取异常，来停止插入操作，连接池中的操作语句会自动停止提交，等待事务结束之后再提交所有操作。
        其中数据库引擎InnoDB是可以支持操作事务的，myisam是不支持的，但其对大量数据的检索操作很好。
        事务回退的三种方法：
        1、设置事务回退条件
        2、扔出一个throw new RuntimeException
        3、自己操作回退，满足条件即可，还可接着返回值操作。
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        该函数不需要写事务，而且数据库引擎不支持
     */
//    @Transactional()//事务注解
    public boolean save(HttpServletRequest request, SolutionAndSourceCode solutionAndSourceCode) throws ParseException {

        LoginUser user = tokenServie.getLoginUser(request);
        SourceCode sourceCode = new SourceCode();
        sourceCode.setSource(solutionAndSourceCode.getSource());
        boolean flag1 = sourceCodeService.save(sourceCode);
        Solution solution = new Solution();
        solution.setSolutionId(sourceCode.getSolutionId());//添加完之后mybatisplus自动回传id
        solution.setProblemId(solutionAndSourceCode.getProblemId());
        solution.setUserId(user.getUsername());
        solution.setNick(user.getUsername());
        solution.setTime(0);
        solution.setMemory(0);
        solution.setResult(0);
        solution.setLanguage(solutionAndSourceCode.getLanguage());
        solution.setIp("");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        solution.setInDate(df.parse(solutionAndSourceCode.getInDate()));
        solution.setContestId(solutionAndSourceCode.getContestId());
        solution.setValid(solutionAndSourceCode.getValid());
        solution.setNum(solutionAndSourceCode.getNum());
        solution.setCodeLength(solutionAndSourceCode.getSource().getBytes(StandardCharsets.UTF_8).length);
        solution.setJudgetime(null);
        solution.setPassRate(new BigDecimal("0"));
        solution.setLintError(0);
        solution.setJudger(solutionAndSourceCode.getJudger());
        solution.setCourseId(solutionAndSourceCode.getCourseId());
        boolean flag2 = super.save(solution);
        return flag1 && flag2;
    }

    public Page<Solution> pageSolutionsCondition(long current, long limit, SolutionQuery solutionQuery) {
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("solution_id");

        if (!solutionQuery.getIsAll()) {
//            将当前用户添加
            LoginUser user = SecurityUntils.getLoginUser();
            System.err.println(user);
            queryWrapper.eq("user_id", user.getUsername());
        } else if (solutionQuery.getNick() != null && !solutionQuery.getNick().equals("")) {
            queryWrapper.like("nick", solutionQuery.getNick());
        }

//        处理搜索
        if (solutionQuery.getProblemId() != null && !solutionQuery.getProblemId().equals("")) {
            queryWrapper.eq("problem_id", solutionQuery.getProblemId());
        }
        if (solutionQuery.getLanguage() != null && !solutionQuery.getLanguage().equals("")) {
            queryWrapper.eq("language", solutionQuery.getLanguage());
        }
        if (solutionQuery.getResult() != null && !solutionQuery.getResult().equals("")) {
            queryWrapper.eq("result", solutionQuery.getResult());
        }

        return page(new Page<Solution>(current, limit), queryWrapper);
    }

    public JsonResult getSubmitAWeek() {
        DateTime dateTime = DateUtil.offsetDay(new Date(), -30);
        String strTime = DateFormatUtils.format(dateTime, "yyyy-MM-dd") + " 00:00:00";
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("solution_id", "result", "in_date");
        queryWrapper.apply("UNIX_TIMESTAMP(in_date) >= UNIX_TIMESTAMP('" + strTime + "')");


        return JsonResult.success(buildSubmissionStatisticsResult(list(queryWrapper)));
    }


    // ----------------------------------------------------------------------------------------------

    private SubmissionStatisticsResult buildSubmissionStatisticsResult(List<Solution> judgeList) {
        int n = 7;
        Long ac[] = new Long[n + 10], total[] = new Long[n + 10];
        String[] day = new String[n];


        Date date = new Date();
        TreeMap<String, Integer> findDate = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            String s = DateFormatUtils.format(DateUtil.offsetDay(date, -i), "MM-dd");
            day[6 - i] = s;
            findDate.put(s, 6 - i);
            ac[i] = 0l;
            total[i] = 0l;
        }

        if (!CollectionUtils.isEmpty(judgeList)) {
            Map<String, List<Solution>> map = judgeList.stream().collect(Collectors.groupingBy(o -> DateUtil.format(o.getInDate(), "MM-dd")));
            for (String NowDay : map.keySet()) {
                if (findDate.containsKey(NowDay)) {
                    List<Solution> solutionList = map.get(NowDay);
                    int x = findDate.get(NowDay);

                    total[x] += solutionList.size();
                    long count = solutionList.parallelStream().filter(judge -> Objects.equals(judge.getResult(), Constants.Judge.STATUS_ACCEPTED.getStatus())).count();
                    ac[x] += count;
                }
            }
        }

        SubmissionStatisticsResult submissionStatisticsResult = new SubmissionStatisticsResult();

        submissionStatisticsResult.setDateStrList(Arrays.asList(day));

        submissionStatisticsResult.setAcCountList(Arrays.asList(ac));

        submissionStatisticsResult.setTotalCountList(Arrays.asList(total));

        return submissionStatisticsResult;
    }

}
