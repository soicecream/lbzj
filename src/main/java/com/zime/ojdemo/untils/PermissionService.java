package com.zime.ojdemo.untils;

import com.zime.ojdemo.modle.vo.core.security.LoginUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Service("ss")
public class PermissionService {


    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission)
    {
        LoginUser loginUser = SecurityUntils.getLoginUser();
        if (CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        System.out.println(hasPermissions(loginUser.getPermissions(), permission));
        System.out.println(loginUser.getPermissions());
        System.out.println(permission);
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(StringUtils.trim(permission));
    }
}
