package com.zime.ojdemo.cs;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class a{
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    int left=0,right=0,lwz=0,rwz=0;
    int n=word1.length;
    int n1=word2.length;
    while (true){
        if(lwz>=word1[left].length()){
            left++;
            lwz=0;
        }
        if(rwz>=word2[right].length()){
            right++;
            rwz=0;
        }
        if(left>=n||right>=n1) break;
        if(word1[left].charAt(lwz)!=word2[right].charAt(rwz)) return false;
        lwz++;
        rwz++;
    }
    if(left==n&&right==n1) return true;
    else return false;
    }
}
