package com.zime.ojdemo.service.impl;

import com.zime.ojdemo.entity.RoleMenu;
import com.zime.ojdemo.mapper.RoleMenuMapper;
import com.zime.ojdemo.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
