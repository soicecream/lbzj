package com.zime.ojdemo.service.impl;

import com.zime.ojdemo.entity.UserRole;
import com.zime.ojdemo.mapper.UserRoleMapper;
import com.zime.ojdemo.modle.vo.VueUserRole;
import com.zime.ojdemo.modle.vo.query.UserRoleQuery;
import com.zime.ojdemo.modle.vo.result.VueUserRoles;
import com.zime.ojdemo.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<VueUserRoles> pageUserRoles(long current, long limit, UserRoleQuery userRoleQuery) {
        String username=userRoleQuery.getUsername();
        String rolename=userRoleQuery.getRolename();
        long start=(current-1)*limit;
        long end=start+limit;

        List<VueUserRole> userroles=userRoleMapper.pageUserRoles(start,end,rolename,username);
        List<VueUserRoles> zhis=new ArrayList<>();
        HashMap<Long,Integer> wz=new HashMap<>();
        for (VueUserRole userRole:userroles){
            Long userid=userRole.getUserId();
            if(!wz.containsKey(userid)){
                wz.put(userid,zhis.size());
                VueUserRoles vueUserRoles=new VueUserRoles();
                vueUserRoles.setUserId(userid);
                vueUserRoles.setUserName(userRole.getUserName());
                vueUserRoles.setRoleIds(new ArrayList<Long>(){{add(userRole.getRoleId());}});
                vueUserRoles.setRoleNames(new ArrayList<String>(){{add(userRole.getRoleName());}});
                zhis.add(vueUserRoles);
            }
            else{
                VueUserRoles zhi=zhis.get(wz.get(userid));
                zhi.getRoleIds().add(userRole.getRoleId());
                zhi.getRoleNames().add(userRole.getRoleName());
            }
        }
        return zhis;
    }


}
