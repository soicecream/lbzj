package com.zime.ojdemo.modle.vo;

import lombok.Data;

@Data
public class VueUserRole {
    private Long userId;
    private Long roleId;
    private String userName;
    private String roleName;
}
