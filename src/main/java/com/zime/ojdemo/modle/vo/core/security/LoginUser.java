package com.zime.ojdemo.modle.vo.core.security;

import com.zime.ojdemo.entity.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class LoginUser implements UserDetails {
    private Long userId;
    private String token;
    private Long loginTime;
    private Long expireTime;

    private Users users;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    public LoginUser(Long userId, Set<String> permissions,Users user){
        this.userId=userId;
        this.permissions=permissions;
        this.users=user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
