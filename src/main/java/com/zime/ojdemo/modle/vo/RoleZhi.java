package com.zime.ojdemo.modle.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleZhi {
    private String roleName;
    private Long roleId;
    private String remark;
    private String roleKey;
    private List<Long> roles;
}
