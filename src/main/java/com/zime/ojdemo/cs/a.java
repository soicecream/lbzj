package com.zime.ojdemo.cs;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class a{
    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

    public int largestAltitude(int[] gain) {
    int sum=0,max=0;
    for(int i=0;i<gain.length;i++){
        sum+=gain[i];
        if(max<gain[i]) max=sum;
    }
    return max;
    }
}
