package com.zime.ojdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zime.ojdemo.entity.Menu;
import com.zime.ojdemo.entity.Role;
import com.zime.ojdemo.entity.RoleMenu;
import com.zime.ojdemo.modle.vo.PageList;

import com.zime.ojdemo.modle.vo.RoleZhi;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import com.zime.ojdemo.service.IMenuService;
import com.zime.ojdemo.service.IRoleMenuService;
import com.zime.ojdemo.service.IRoleService;
import com.zime.ojdemo.untils.SecurityUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IMenuService menuService;

    @Autowired
    IRoleMenuService roleMenuService;

    @PreAuthorize("@ss.hasPermi('role:list')")
    @PostMapping("pagerole/{current}/{limit}")
    public JsonResult<PageList> pageuser(@PathVariable Integer current, @PathVariable Integer limit,
                                         @RequestBody UserQuery userQuery){
        return JsonResult.success(roleService.pageusers(current,limit,userQuery,true));
    }


    @PreAuthorize("@ss.hasPermi('role:list')")
    @PostMapping("adminpageuser/{current}/{limit}")
    public JsonResult<PageList> adminpageuser(@PathVariable Integer current, @PathVariable Integer limit,
                                         @RequestBody UserQuery userQuery){
        return JsonResult.success(roleService.pageusers(current,limit,userQuery,false));
    }



   @GetMapping("getRoles")
   public JsonResult<List<Role>> getRoles(){
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        wrapper.eq("status",1);
        return JsonResult.success(roleService.list(wrapper));
   }


   @GetMapping("getRole")
   public JsonResult<List<Menu>> getRole(){
        List<Menu> menus=menuService.selectMenuTreeByUserId(-1L);
       return JsonResult.success(menus);
   }

   @GetMapping("getRoleMenu/{roleid}")
   public JsonResult<List<Long>> getRoleMenu(@PathVariable Long roleid){
       List<RoleMenu> rolemenus=roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id",roleid));
       ArrayList<Long> menuids=new ArrayList<>();
       for(RoleMenu rolemenu:rolemenus){
           menuids.add(rolemenu.getMenuId());
       }
       return JsonResult.success(menuids);
   }

    @PreAuthorize("@ss.hasPermi('role:add')")
   @PostMapping("addRole")
    public JsonResult<Boolean> addRole(@RequestBody RoleZhi roleZhi){
        roleService.addrole(roleZhi);
        return JsonResult.success(true);
   }

    @PreAuthorize("@ss.hasPermi('role:del')")
   @PostMapping("delRole")
   public JsonResult<Boolean> delRole(@RequestBody ArrayList<Long> roleid){
       roleService.removeByIds(roleid);
       for (Long role:roleid){
           QueryWrapper<RoleMenu> wrapper=new QueryWrapper<>();
           wrapper.eq("role_id",role);
           roleMenuService.remove(wrapper);
       }
       return JsonResult.success(true);
   }

    @PreAuthorize("@ss.hasPermi('role:up')")
   @PostMapping("upRole")
   public JsonResult<Boolean> upRole(@RequestBody RoleZhi roleZhi){
       roleService.uprole(roleZhi);
       return JsonResult.success(true);
   }

    @PreAuthorize("@ss.hasPermi('role:up')")
    @PostMapping("upRoleS/{roleid}/{status}")
    public JsonResult<Boolean> upRole(@PathVariable Long roleid,@PathVariable Boolean status){
        UpdateWrapper<Role> wrapper=new UpdateWrapper<>();
        wrapper.eq("role_id",roleid);
        wrapper.set("status",status);
        roleService.update(wrapper);
        return JsonResult.success(true);
    }
}
