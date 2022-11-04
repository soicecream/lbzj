package com.zime.ojdemo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.modle.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=usersService.getOne(new QueryWrapper<Users>().eq("user_id",username));
        if(user==null){

        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(Users user)
    {
        System.out.println(user);
        return new LoginUser(user.getUserId(),user.getPassword());
    }
}
