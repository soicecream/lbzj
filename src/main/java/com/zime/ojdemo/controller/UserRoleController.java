package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zime.ojdemo.entity.UserRole;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.RoleZhi;
import com.zime.ojdemo.modle.vo.VueUserRole;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.ProblemQuery;
import com.zime.ojdemo.modle.vo.query.UserRoleQuery;
import com.zime.ojdemo.modle.vo.result.VueUserRoles;
import com.zime.ojdemo.service.IRoleService;
import com.zime.ojdemo.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/userrole")
public class UserRoleController {

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleService roleService;

    @PreAuthorize("@ss.hasPermi('ur:list')")
    @PostMapping("pageUserRole/{current}/{limit}")
    public JsonResult<List<VueUserRoles>> pageProblemsCondition(@PathVariable long current, @PathVariable long limit,
                                                                @RequestBody(required = false)UserRoleQuery userRoleQuery) {
        return JsonResult.success(userRoleService.pageUserRoles(current,limit,userRoleQuery));
    }

    @PreAuthorize("@ss.hasPermi('ur:add')")
    @PostMapping("addUserRole")
    public JsonResult<Boolean> addRole(@RequestBody VueUserRoles vueUserRoles){
        Long userid=vueUserRoles.getUserId();
        for(Long roid:vueUserRoles.getRoleIds()){
            UserRole userRole=new UserRole();
            userRole.setRoleId(roid);
            userRole.setUserId(userid);
            userRoleService.save(userRole);
        }
        return JsonResult.success(true);
    }

    @PreAuthorize("@ss.hasPermi('ur:del')")
    @PostMapping("delUserRole")
    public JsonResult<Boolean> delRole(@RequestBody List<Long> ids){
        QueryWrapper<UserRole> wrapper=new QueryWrapper();
        for(Long id:ids){
            wrapper.eq("user_id",id);
            userRoleService.remove(wrapper);
        }
        return JsonResult.success(true);
    }

    @PreAuthorize("@ss.hasPermi('ur:up')")
    @PostMapping("upUserRole")
    public JsonResult<Boolean> upRole(@RequestBody VueUserRoles vueUserRoles){
        QueryWrapper<UserRole> wrapper=new QueryWrapper();
        Long userid=vueUserRoles.getUserId();
        wrapper.eq("user_id",userid);
        userRoleService.remove(wrapper);
        for(Long roid:vueUserRoles.getRoleIds()){
             UserRole userRole=new UserRole();
             userRole.setRoleId(roid);
             userRole.setUserId(userid);
             userRoleService.save(userRole);
        }
        return JsonResult.success(true);
    }
}
