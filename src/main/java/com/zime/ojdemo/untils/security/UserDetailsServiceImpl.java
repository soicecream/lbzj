package com.zime.ojdemo.untils.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.exception.ServiceException;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.service.IMenuService;
import com.zime.ojdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersService usersService;

    @Autowired
    IMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=usersService.getOne(new QueryWrapper<Users>().eq("user_name",username));
        if(user==null){
          throw new ServiceException("用户："+username+"不存在");
        }
        if(!user.getDefunct()){
            throw new ServiceException("用户："+username+"被停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(Users user)
    {
        return new LoginUser(user.getUserId(),menuService.selectMenuPermsByUserId(user.getUserId()),user);
    }
}
