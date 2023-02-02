package com.zime.ojdemo.cs;

import lombok.Data;

@Data
public class MyBean {
    String name;
    public void say(String name){
        System.out.println("输出为："+name);
    }
}
