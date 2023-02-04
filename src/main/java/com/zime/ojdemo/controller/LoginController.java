package com.zime.ojdemo.controller;



import com.zime.ojdemo.entity.Menu;
import com.zime.ojdemo.modle.vo.core.security.LoginBody;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import com.zime.ojdemo.service.IMenuService;
import com.zime.ojdemo.service.IRoleService;
import com.zime.ojdemo.untils.LoginServie;
import com.zime.ojdemo.untils.SecurityUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class LoginController {

    @Autowired
    LoginServie loginServie;

    @Autowired
    IRoleService roleService;

    @Autowired
    IMenuService menuService;

    @PostMapping("/login")
    public JsonResult<String> Login(@RequestBody LoginBody loginBody){
        String token=loginServie.login(loginBody.getUsername(),loginBody.getPassword());
        if(token.equals("password")) return JsonResult.error(401,"密码错误");
        else if(token.equals("name")) return JsonResult.error(401,"用户不存在");
        return  JsonResult.success(token);
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public JsonResult<HashMap<String,Object>> getInfo()
    {
        LoginUser user = SecurityUntils.getLoginUser();
        Long userid=user.getUserId();
        // 角色集合
        Set<String> roles = roleService.selectRolePermissionByUserId(userid);
        if(roles.size()==0) roles.add("ji");
        // 权限集合
        Set<String> permissions = menuService.selectMenuPermsByUserId(userid);

        HashMap<String,Object> zhi=new HashMap<>();
        zhi.put("user",user);
        zhi.put("roles",roles);
        zhi.put("permissions",permissions);
        return JsonResult.success(zhi);
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public JsonResult<List<Menu>> getRouters()
    {
        LoginUser user = SecurityUntils.getLoginUser();
        Long userid=user.getUserId();
        List<Menu> menus = menuService.selectMenuTreeByUserId(userid);
        System.out.println("---------------------------------------------------------------------------");
        for(Menu i : menus) {
            System.out.println(i);
            if(i.getChildren() != null) {
                for(Menu j : i.getChildren()) {
                    System.out.println("\t " + j);
                }
            }
        }
        return JsonResult.success(menus);
    }


}
