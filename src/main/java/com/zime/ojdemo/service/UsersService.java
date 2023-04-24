package com.zime.ojdemo.service;

import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Component
public interface UsersService extends IService<Users> {


    String login(User user);

    Boolean logout(String token);

    PageList pageusers( Integer current,Integer limit, UserQuery userQuery);

    Users getuser(HttpServletRequest request,String id);

    List<Solution> getuserPro(String userid);

    Boolean delUsers(ArrayList<String> ids);

    Boolean addUser(Users user);

    Boolean imageUpload(MultipartFile file, String userid) throws IllegalStateException;


    JsonResult getTenTopUser();
}
