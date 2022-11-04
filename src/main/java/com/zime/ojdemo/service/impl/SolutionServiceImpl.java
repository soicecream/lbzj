package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.SolutionAndSourceCode;
import com.zime.ojdemo.entity.SourceCode;
import com.zime.ojdemo.mapper.SolutionMapper;
import com.zime.ojdemo.modle.vo.LoginUser;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.SolutionQuery;
import com.zime.ojdemo.service.SolutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.service.SourceCodeService;
import com.zime.ojdemo.service.TokenServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
    public boolean save(HttpServletRequest request,SolutionAndSourceCode solutionAndSourceCode) throws ParseException {

        LoginUser user=tokenServie.getLoginUser(request);
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
        return flag1&&flag2;
    }

    public PageList pageSolutionsCondition(long current, long limit, SolutionQuery solutionQuery){
        Page<Solution> pageSolution = new Page<>(current, limit);
        QueryWrapper<Solution> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("in_date");
        //调用方法时候，底层封装，把分页所有数据封装到pageUsers对象里面
        if(solutionQuery.getUserId()!=null&&!solutionQuery.getUserId().equals("")) wrapper.eq("user_id",solutionQuery.getUserId());

        if(solutionQuery.getContestId()!=null) wrapper.eq("contest_id",solutionQuery.getContestId());
        if(solutionQuery.getProblemId()!=null&&!solutionQuery.getProblemId().equals("")) wrapper.eq("problem_id",solutionQuery.getProblemId());
        if(solutionQuery.getLanguage()!=null&&solutionQuery.getLanguage()!=-1) wrapper.eq("language",solutionQuery.getLanguage());
        if(solutionQuery.getResult()!=null&&solutionQuery.getResult()!=-1) wrapper.eq("result",solutionQuery.getResult());
        page(pageSolution, wrapper);
        long total = pageSolution.getTotal();
        List<Solution> records = pageSolution.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
    }
}
