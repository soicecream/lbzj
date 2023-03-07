package com.zime.ojdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zime.ojdemo.entity.Solution;
import com.zime.ojdemo.entity.Users;
import com.zime.ojdemo.mapper.UsersMapper;
import com.zime.ojdemo.modle.vo.*;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.modle.vo.query.UserQuery;
import com.zime.ojdemo.service.SolutionService;
import com.zime.ojdemo.untils.Io;
import com.zime.ojdemo.untils.TokenServie;
import com.zime.ojdemo.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zime
 * @since 2021-06-08
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    TokenServie tokenServie;

    @Autowired
    SolutionService solutionService;


    public String login(User user) {
        return "";
    }

    public Boolean logout(String token) {
        return true;
    }


    public PageList pageusers(Integer current, Integer limit, UserQuery userQuery) {
        Page<Users> pageSolution = new Page<>(current, limit);
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        if (!userQuery.getUsername().equals("") && userQuery.getUsername() != null)
            wrapper.eq("nick", userQuery.getUsername());
        page(pageSolution, wrapper);
        long total = pageSolution.getTotal();
        List<Users> records = pageSolution.getRecords();
        PageList pageList = new PageList();
        pageList.setTotal(total);
        pageList.setRows(records);
        return pageList;
    }

    public Users getuser(HttpServletRequest request, String id) {
        System.err.println("--------------" + id);
        QueryWrapper<Users> s = new QueryWrapper<>();
        if (id == null || id.equals("undefined")) {
            LoginUser loginUser = tokenServie.getLoginUser(request);
            s.eq("user_id", loginUser.getUserId());
            System.err.println("--------------------------------------------------------------");
        } else s.eq("user_id", Long.valueOf(id));
        return getOne(s);
    }

    public List<Solution> getuserPro(String userid) {
        QueryWrapper<Solution> s = new QueryWrapper<>();
        s.eq("user_id", userid);
        s.select("problem_id");
        s.groupBy("problem_id");
        List<Solution> zhi = solutionService.list(s);
        return zhi;
    }

    public Boolean delUsers(ArrayList<String> ids) {

        return removeByIds(ids);
    }

    public Boolean addUser(Users user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setAccesstime(new Date());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAvatar("mo.jpeg");
        return save(user);
    }


    private String filePath = "D:\\java1\\lbzj\\web\\src\\assets\\img\\image/";

    public Boolean imageUpload(MultipartFile file, String userid) throws IllegalStateException {
        File filee = new File(filePath);
        if (!filee.exists()) {
            filee.mkdirs();
        }

        //文件名称
        String realFileName = file.getOriginalFilename();
        //文件后缀
        String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);
        /***************文件处理*********************/

        String newname = userid + "." + suffix;
        String newFilePath = filePath + newname;

        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        Users users = getOne(wrapper);

        if (!(users.getAvatar().length() > 0)) {
            Io.deleteFile(new File(filePath + users.getAvatar()));
        }
        users.setAvatar(newname);
        update(users, wrapper);


        try {
            file.transferTo(new File(newFilePath));
            //将传来的文件写入新建的文件
            System.out.println("上传图片成功进行上传文件测试");

        } catch (IllegalStateException e) {
            //处理异常
        } catch (IOException e1) {
            //处理异常
        }
        return false;
    }

    @Override
    public JsonResult getTenTopUser() {
//        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("is_rank", 1);
//        page(new Page<>(1, 10), queryWrapper);
        return null;
    }

}
