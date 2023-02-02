package com.zime.ojdemo.modle.vo.result;

import lombok.Data;

import java.util.List;

@Data
public class VueUserRoles {
    private Long userId;

    private String userName;

    private List<Long> roleIds;

    private List<String> roleNames;
}
