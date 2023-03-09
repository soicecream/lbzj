package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import com.zime.ojdemo.service.UsersService;
import com.zime.ojdemo.untils.TokenServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("@ss.hasPermi('user:list')")
    @PostMapping("pageuser/{current}/{limit}")
    public JsonResult<PageList> pageuser(@PathVariable Integer current, @PathVariable Integer limit,
                                         @RequestBody UserQuery userQuery) {
        return JsonResult.success(usersService.pageusers(current, limit, userQuery));
    }

    //返回对应用户
    @GetMapping("getuser/{id}")
    public JsonResult<Users> getuser(HttpServletRequest Request, @PathVariable String id) {
        return JsonResult.success(usersService.getuser(Request, id));
    }

    //用户做对题数
    @PostMapping("getuserPro/{userid}")
    public JsonResult<List<Solution>> getuserPro(@PathVariable String userid) {
        return JsonResult.success(usersService.getuserPro(userid));
    }

    @PreAuthorize("@ss.hasPermi('user:del')")
    @PostMapping("deluser")
    public JsonResult<Boolean> deluser(@RequestBody ArrayList<String> ids) {
        return JsonResult.success(usersService.delUsers(ids));
    }

    @PreAuthorize("@ss.hasPermi('user:add')")
    @PostMapping("adduser")
    public JsonResult<Boolean> adduser(@RequestBody Users user) {
        return JsonResult.success(usersService.addUser(user));
    }


    @PreAuthorize("@ss.hasPermi('user:up')")
    @PostMapping("updateuser")
    public JsonResult<Boolean> updateuser(@RequestBody Users user) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId());
        return JsonResult.success(usersService.saveOrUpdate(user));
    }

    //个人账号设置
    @PostMapping("formuser/{password}")
    public JsonResult<Boolean> formuser(@RequestBody Users user, @PathVariable String password) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId());
        Users users = usersService.getOne(wrapper);
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        if (b.matches(user.getPassword(), b.encode(users.getPassword()))) {
            user.setPassword(b.encode(password));
            return JsonResult.success(usersService.saveOrUpdate(user));
        }
        return JsonResult.error("密码错误");
    }


    //图片接收
    @RequestMapping(value = "upload2")
    public void imageUpload(MultipartFile file, String userid) throws IllegalStateException {
        usersService.imageUpload(file, userid);
    }


    @GetMapping("get/top")
    public JsonResult getTenTopUser() {
        return usersService.getTenTopUser();
    }

}

