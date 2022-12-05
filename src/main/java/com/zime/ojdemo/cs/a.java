package com.zime.ojdemo.cs;


import com.zime.ojdemo.entity.Solution;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class a{
    public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }


    public int minOperations(String s) {
     int a=0;int b=0;
     for (int i=0;i<s.length();i++){
         if(i%2==0&&s.charAt(i)!='0') a++;
         else if(i%2!=0&&s.charAt(i)!='1') a++;

         if(i%2==0&&s.charAt(i)!='1') b++;
         else if(i%2!=0&s.charAt(i)!='0') b++;
     }
     return Math.min(a,b);
    }


}
