package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.PageList;
import com.zime.ojdemo.modle.vo.RoleZhi;
import com.zime.ojdemo.modle.vo.query.UserQuery;

import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
public interface IRoleService extends IService<Role> {


    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);


    PageList pageusers(Integer current, Integer limit, UserQuery userQuery,Boolean pd);

    Boolean addrole(RoleZhi roleZhi);

    Boolean uprole(RoleZhi roleZhi);
}
