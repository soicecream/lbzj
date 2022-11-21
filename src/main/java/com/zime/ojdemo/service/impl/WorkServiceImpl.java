package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.mapper.WorkMapper;
import com.zime.ojdemo.modle.vo.result.WorkListResult;
import com.zime.ojdemo.modle.vo.result.WorkRankresult;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {

    @Autowired
    SolutionService solutionService;

    @Autowired
    ProblemMapper problemMapper;


    public ArrayList<WorkRankresult> getworkrank(Integer workid){
//        QueryWrapper<Solution> wrapper=new QueryWrapper<>();
//        wrapper.eq("contest_id",workid);
//        wrapper.eq("result",4);
//        wrapper.select("problem_id,user_id,min(in_date) as in_date");
//        wrapper.groupBy("problem_id,user_id");
//        List<Solution> szhi=solutionService.list(wrapper);
//
//        String work=getProblem(workid);
//        String[] problemids=work.split("&");
//        HashMap<String,Integer> promap=new HashMap<>();
//        for(int i=0;i<problemids.length;i++) promap.put(problemids[i],i);
//
//        ArrayList<WorkRankresult> user=new ArrayList<>();
//
//        HashMap<String,HashMap<Integer,Integer>> user=new HashMap<>();
//
//        for(Solution s:szhi){
//            if(user.containsKey(s.getUserId())){
//            user.get(s.getUserId()).put(s.getProblemId(),s.getTime());
//            }
//            else{
//                HashMap<Integer,Integer> zhi=new HashMap<>();
//                zhi.put(s.getProblemId(),s.getTime());
//                user.put(s.getUserId(),zhi);
//            }
//        }

        ArrayList<WorkRankresult> user=new ArrayList<>();
        WorkRankresult workRankresult=new WorkRankresult();
        workRankresult.setUsername("admin");
        workRankresult.setTimes(100);
        ArrayList<Integer> zhi=new ArrayList<>(2);
        ArrayList<Integer> zhi1=new ArrayList<>(2);
        zhi.add(1);
        zhi1.add(2);
        ArrayList<Integer> c=new ArrayList<>(2);
        ArrayList<Integer> c1=new ArrayList<>(2);
        c.add(1);
        c1.add(2);
        workRankresult.setCount(c);
        workRankresult.setTime(zhi);
        user.add(workRankresult);
        return user;
    }

    public String getProblem(Integer workid){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workid",workid);
        return super.getOne(wrapper).getProbelmid();
    }

    public Work getWork(Integer id){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workid",id);
        return getOne(wrapper);
    }

    public List<WorkListResult> pageWorkid(String problemid){
        String[] problemids=problemid.split("&");
        QueryWrapper<Problem> wrapper=new QueryWrapper<>();
        wrapper.in("problem_id",problemids);
        List<WorkListResult> zhi=new LinkedList<>();
        for(Problem p:problemMapper.selectList(wrapper)){
            WorkListResult work=new WorkListResult();
            work.setTitle(p.getTitle());
            work.setProblemId(p.getProblemId());
            work.setDegree(p.getDegree());
            zhi.add(work);
        }
        return zhi;
    }
}
