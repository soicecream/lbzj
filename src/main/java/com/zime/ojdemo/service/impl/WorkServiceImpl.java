package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Problem;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Work;
import com.zime.ojdemo.mapper.ProblemMapper;
import com.zime.ojdemo.mapper.WorkMapper;
import com.zime.ojdemo.modle.vo.WorkListResult;
import com.zime.ojdemo.modle.vo.WorkRankresult;
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
        wrapper.eq("result",4);
        wrapper.select("distinct problem_id,user_id");
        List<Solution> szhi=solutionService.list(wrapper);
        HashMap<String,ArrayList<Integer>> user=new HashMap<>();

        List<WorkListResult> pzhi=pageWorkid(getProblem(workid));
        HashMap<Integer,Integer> pmap=new HashMap<>();
        for(WorkListResult r:pzhi){
            pmap.put(r.getProblemId(),r.getDegree());
        }

        int wz=0;
        for(Solution s:szhi){
            wz++;
            if(user.containsKey(s.getUserId())){
            ArrayList<Integer> u=user.get(s.getUserId());
            u.set(0,u.get(0)+pmap.get(s.getProblemId()));
            u.set(1,wz);
            }
            else{
                ArrayList<Integer> u=new ArrayList<>();
                u.add(pmap.get(s.getProblemId()));
                u.add(wz);
                user.put(s.getUserId(),u);
            }
        }

        ArrayList<String> username=new ArrayList<>();
        ArrayList<Integer> intrgeral=new ArrayList<>();
        for (Map.Entry<String,ArrayList<Integer>> u:user.entrySet()){
            username.add(u.getKey());
            intrgeral.add(u.getValue().get(0));
        }


        for (int i=0;i<intrgeral.size();i++){
            for (int j=i;j<intrgeral.size()-1;j++){
                if(intrgeral.get(j)<intrgeral.get(j+1)){
                    int jh=intrgeral.get(j);
                    intrgeral.set(j,intrgeral.get(j+1));
                    intrgeral.set(j+1,jh);

                    String jh1=username.get(j);
                    username.set(j,username.get(j+1));
                    username.set(j+1,jh1);
                }
                if(intrgeral.get(j)==intrgeral.get(j+1)&&user.get(username.get(j)).get(1)>user.get(username.get(j+1)).get(1)){
                    int jh=intrgeral.get(j);
                    intrgeral.set(j,intrgeral.get(j+1));
                    intrgeral.set(j+1,jh);

                    String jh1=username.get(j);
                    username.set(j,username.get(j+1));
                    username.set(j+1,jh1);
                }
            }
        }

        ArrayList<WorkRankresult> rankresults=new ArrayList<>();
        for(int i=0;i<username.size();i++){
            WorkRankresult workRankresult=new WorkRankresult();
            workRankresult.setUsername(username.get(i));
            workRankresult.setIntrgeral(intrgeral.get(i));
            rankresults.add(workRankresult);
        }
        System.out.println("6666666666666666666666666");
        System.out.println(rankresults);
        return rankresults;
    }

    public String getProblem(Integer workid){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workid",workid);
        return super.getOne(wrapper).getProbelmid();
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
