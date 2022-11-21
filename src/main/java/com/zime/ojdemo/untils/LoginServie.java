package com.zime.ojdemo.untils;


import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginServie {

    @Autowired
    private TokenServie tokenServie;

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String username, String password) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                System.out.println(e.getMessage());
            } else {
                System.out.println(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenServie.createToken(loginUser);
    }

    public Boolean logout(HttpServletRequest request) {
        return tokenServie.delToken(request);
    }
}
