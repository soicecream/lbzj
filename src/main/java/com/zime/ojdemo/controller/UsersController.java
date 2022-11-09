package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import com.zime.ojdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("pageuser/{current}/{limit}")
    public JsonResult<PageList> pageuser(@PathVariable Integer current,@PathVariable Integer limit,
                                         @RequestBody UserQuery userQuery){
     return JsonResult.success(usersService.pageusers(current,limit,userQuery));
    }

    @GetMapping("getuser/{id}")
    public JsonResult<Users> getuser(HttpServletRequest Request,String id){
        return JsonResult.success(usersService.getuser(Request,id));
    }

    @PostMapping("getuserPro/{current}/{limit}")
    public JsonResult<PageList> getuserPro(HttpServletRequest request,@PathVariable Integer current,@PathVariable Integer limit){
        return JsonResult.success(usersService.getuserPro(request,current,limit));
    }

    @PostMapping("deluser")
    public JsonResult<Boolean> deluser(@RequestBody ArrayList<String> ids){
        return JsonResult.success(usersService.delUsers(ids));
    }

    @PostMapping("adduser")
    public JsonResult<Boolean> adduser(@RequestBody Users user){
        return JsonResult.success(usersService.addUser(user));
    }

    @PostMapping("updateuser")
    public JsonResult<Boolean> updateuser(@RequestBody Users user){
        QueryWrapper<Users> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
        return JsonResult.success(usersService.saveOrUpdate(user));
    }
}

