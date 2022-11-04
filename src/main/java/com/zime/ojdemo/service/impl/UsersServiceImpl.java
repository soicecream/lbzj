package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.mapper.UsersMapper;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.service.TokenServie;
import com.zime.ojdemo.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    TokenServie tokenServie;

    @Autowired
    SolutionService solutionService;



    public String login(User user){
        return "";
    }
    public Boolean logout(String token){
        return true;
    }


    public PageList pageusers( Integer current,Integer limit, UserQuery userQuery){
        Page<Users> pageSolution = new Page<>(current, limit);
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        if(userQuery.getNick()!=""&&userQuery.getNick()!=null) wrapper.eq("nick",userQuery.getNick());
        page(pageSolution, wrapper);
        long total = pageSolution.getTotal();
        List<Users> records = pageSolution.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
    }

    public Users getuser(HttpServletRequest request,String id){
        QueryWrapper<Users> s = new QueryWrapper<>();
        if(id==null) {
            LoginUser loginUser = tokenServie.getLoginUser(request);
            s.eq("user_id", loginUser.getUsername());
        }
        else s.eq("user_id",id);
        return getOne(s);
    }

   public PageList getuserPro( HttpServletRequest request,Integer current,Integer limit){
    LoginUser loginUser=tokenServie.getLoginUser(request);
    QueryWrapper<Solution> s=new QueryWrapper<>();
    s.eq("user_id",loginUser.getUsername());
    s.select("problem_id");
    s.groupBy("problem_id");
    Page<Solution> pageSolution = new Page<>(current, limit);
       solutionService.page(pageSolution, s);
       long total = pageSolution.getTotal();
       List<Solution> records = pageSolution.getRecords();
       PageList pageList = new PageList();
       pageList.setTotal(total);
       pageList.setRows(records);
       return pageList;
   }

   public Boolean delUsers(ArrayList<String> ids){

      return removeByIds(ids);
   }

   public Boolean addUser(Users user){
        System.out.println(user);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return save(user);
   }
}
