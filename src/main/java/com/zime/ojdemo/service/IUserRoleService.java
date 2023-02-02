package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.VueUserRole;
import com.zime.ojdemo.modle.vo.query.UserRoleQuery;
import com.zime.ojdemo.modle.vo.result.VueUserRoles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
public interface IUserRoleService extends IService<UserRole> {

    List<VueUserRoles> pageUserRoles(long current, long limit, UserRoleQuery userRoleQuery);
}
