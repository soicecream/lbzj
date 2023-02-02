package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.mapper.WorkMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.query.WorkQuery;
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
        QueryWrapper<Solution> wrapper=new QueryWrapper<>();
        wrapper.eq("contest_id",workid);
        wrapper.select("problem_id,user_id,in_date,result");
        List<Solution> szhi=solutionService.list(wrapper);

        String work=getProblem(workid);
        int[] problemids=Arrays.stream(work.split("&")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(problemids);
        HashMap<Integer,Integer> promap=new HashMap<>();
        for(int i=0;i<problemids.length;i++) promap.put(Integer.valueOf(problemids[i]),i);

        Work work1=getById(workid);
        ArrayList<WorkRankresult> user=new ArrayList<>();

        HashMap<String,Integer> usermap=new HashMap<>();

        for(Solution s:szhi){
            if(!usermap.containsKey(s.getUserId())){
                usermap.put(s.getUserId(),usermap.size());
                WorkRankresult workRankresult=new WorkRankresult(s.getUserId(),promap.size());
                user.add(workRankresult);
            }

            WorkRankresult workRankresult=user.get(usermap.get(s.getUserId()));
            int prowz=promap.get(s.getProblemId());
            if(workRankresult.getTime().get(prowz)!=-1) break;

            Integer czhi=workRankresult.getCount().get(prowz);
            czhi++;
            workRankresult.getCount().set(prowz,czhi);

            if(s.getResult()==4){
                Integer time=(int)(s.getInDate().getTime()-work1.getStarttime())+czhi*20000;
                workRankresult.getTime().set(prowz,time);
                workRankresult.setTimes(workRankresult.getTimes()+time);
                workRankresult.setPros(workRankresult.getPros()+1);
            }
        }

        for (int i=0;i<user.size();i++){
            for (int j=0;j<user.size()-1-i;j++){
                if(user.get(j).getPros()<user.get(j+1).getPros()){
                    WorkRankresult w=user.get(j);
                    user.set(j,user.get(j+1));
                    user.set(j+1,w);
                }
                if(user.get(j).getPros()==user.get(j+1).getPros()&&
                        user.get(j).getTimes()>user.get(j+1).getTimes()){
                    WorkRankresult w=user.get(j);
                    user.set(j,user.get(j+1));
                    user.set(j+1,w);
                }
            }
        }
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

    @Override
    public PageList pageWork(long current, long limit, WorkQuery workQuery,Boolean pd) {
        Page<Work> pageProblem = new Page<>(current,limit);
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        if(workQuery.getWorkname()!=null&&!workQuery.getWorkname().equals("")) wrapper.like("name",workQuery.getWorkname());
        wrapper.orderByDesc("endtime");
        if(pd) wrapper.eq("open",1);
        wrapper.le("starttime",new Date().getTime());
        page(pageProblem,wrapper);
        long total = pageProblem.getTotal();
        List<Work> records = pageProblem.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
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
