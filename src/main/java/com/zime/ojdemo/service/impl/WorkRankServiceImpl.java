package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.WorkRank;
import com.zime.ojdemo.mapper.WorkRankMapper;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.service.WorkRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WorkRankServiceImpl extends ServiceImpl<WorkRankMapper, WorkRank> implements WorkRankService {
//    @Autowired
//    SolutionService solutionService;
//
//    @Autowired
//    WorkRankService workRankService;
//
//    public static CopyOnWriteArrayList list=new CopyOnWriteArrayList();
//
//    @Override
//    public synchronized void run() {
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if(list.isEmpty()){
//         QueryWrapper<Solution> wrapper=new QueryWrapper<>();
//         wrapper.eq("solution_id",list);
//         List<Solution> zhi=solutionService.list(wrapper);
//         ArrayList<Solution> zhi1=new ArrayList<>();
//
//         for(int i=0;i<zhi.size();i++){
//          if(zhi.get(i).getResult()!=0){
//              if(zhi.get(i).getResult()==6) zhi1.add(zhi.get(i));
//              zhi.remove(i);
//          }
//         }
//
//         for(int i=0;i<zhi1.size();i++) {
//             UpdateWrapper<WorkRank> workweapper = new UpdateWrapper<>();
//             workweapper.eq("username",zhi1.get(i).getUserId());
//             workweapper.setSql("'intrgeral'='intrgeral'+1");
//             workRankService.update(workweapper);
//         }
//     }
//    }
}
