package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.modle.vo.query.UserQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
public interface UsersService extends IService<Users> {


    String login(User user);

    Boolean logout(String token);

    PageList pageusers( Integer current,Integer limit, UserQuery userQuery);

    Users getuser(HttpServletRequest request,String id);

    PageList getuserPro( HttpServletRequest request,Integer current,Integer limit);

    Boolean delUsers(ArrayList<String> ids);

    Boolean addUser(Users user);

}
