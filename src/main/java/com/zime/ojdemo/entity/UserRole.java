package com.zime.ojdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author wang
 * @since 2023-01-06
 */
@TableName("sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
            "userId=" + userId +
            ", roleId=" + roleId +
        "}";
    }
}
