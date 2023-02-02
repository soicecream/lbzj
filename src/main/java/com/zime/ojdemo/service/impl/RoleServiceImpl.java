package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Menu;
import com.zime.ojdemo.entity.Role;
import com.zime.ojdemo.entity.RoleMenu;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.mapper.RoleMapper;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.RoleZhi;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import com.zime.ojdemo.service.IRoleMenuService;
import com.zime.ojdemo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

     @Autowired
     RoleMapper roleMapper;

     @Autowired
    IRoleMenuService roleMenuService;

    public Set<String> selectRolePermissionByUserId(Long userId){
        List<Role> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms)
        {
            permsSet.add(perm.getRoleKey());
        }
        return permsSet;
    }

    @Override
    public PageList pageusers(Integer current, Integer limit, UserQuery userQuery,Boolean pd) {
        Page<Role> pageSolution = new Page<>(current, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(userQuery.getUsername()!=null&&!userQuery.getUsername().equals("")) wrapper.eq("role_name",userQuery.getUsername());
        if(pd) wrapper.eq("status",1);
        page(pageSolution, wrapper);
        long total = pageSolution.getTotal();
        List<Role> records = pageSolution.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
    }

    @Override
    public Boolean addrole(RoleZhi roleZhi) {
        Role role=new Role();
        role.setRemark(roleZhi.getRemark());
        role.setRoleName(roleZhi.getRoleName());
        role.setRemark(roleZhi.getRemark());
        role.setRoleKey(roleZhi.getRoleKey());
        role.setCreateTime(LocalDateTime.now());

        if(save(role)){
            Role role1=getOne(new QueryWrapper<Role>().eq("role_name",role.getRoleName()));
            Long roleid=role1.getRoleId();
            ArrayList<RoleMenu> menus=new ArrayList<>();
            for(Long menuzhi:roleZhi.getRoles()){
                RoleMenu menu=new RoleMenu();
                menu.setMenuId(menuzhi);
                menu.setRoleId(roleid);
                menus.add(menu);
            }
            roleMenuService.saveBatch(menus);
        }
       return false;
    }

    @Override
    public Boolean uprole(RoleZhi roleZhi) {
        Long roleid=roleZhi.getRoleId();
        UpdateWrapper<Role> wrapper=new UpdateWrapper<>();
        wrapper.eq("role_id",roleid);
        wrapper.set("role_name",roleZhi.getRoleName());
        wrapper.set("role_key",roleZhi.getRoleKey());
        wrapper.set("remark",roleZhi.getRemark());
        if(update(wrapper)){
            roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleid));
            ArrayList<RoleMenu> menus=new ArrayList<>();
            for(Long menuzhi:roleZhi.getRoles()){
                RoleMenu menu=new RoleMenu();
                menu.setMenuId(menuzhi);
                menu.setRoleId(roleid);
                menus.add(menu);
            }
            roleMenuService.saveBatch(menus);
        }
        return false;
    }
}
