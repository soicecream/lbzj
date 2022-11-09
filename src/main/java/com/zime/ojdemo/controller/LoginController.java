package com.zime.ojdemo.controller;



import com.zime.ojdemo.modle.vo.core.security.LoginBody;
import com.zime.ojdemo.modle.vo.base.JsonResult;
import com.zime.ojdemo.untils.LoginServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginServie loginServie;

    @PostMapping("/login")
    public JsonResult<String> Login(@RequestBody LoginBody loginBody){
        String token=loginServie.login(loginBody.getUsername(),loginBody.getPassword());
        return  JsonResult.success(token);
    }
}
