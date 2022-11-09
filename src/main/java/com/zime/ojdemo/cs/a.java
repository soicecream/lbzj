package com.zime.ojdemo.cs;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class a{
    public static void main(String[] args) {
    parseBoolExpr(
            "!(f)");
    }

    public static boolean parseBoolExpr(String expression) {
    int n=expression.length();
    Stack<Character> zhi=new Stack<>();
    for(int i=0;i<n;i++){
            if(expression.charAt(i)==')'){
            int[] pd=new int[2];
            while (zhi.peek()!='('){
                Character pd1=zhi.pop();
                if(pd1=='t') pd[1]=1;
                else pd[0]=1;
            }
            zhi.pop();
            Character pd1=zhi.pop();
            if(pd1=='&') {
                if(pd[0]==1) zhi.push('f');
                else  zhi.push('t');
            }
            else if(pd1=='|'){
                if(pd[1]==1) zhi.push('t');
                else  zhi.push('f');
            }
            else if(pd1=='!'){
                if(pd[1]==1) zhi.push('f');
                else  zhi.push('t');
            }
        }
        else if(expression.charAt(i)!=',') zhi.push(expression.charAt(i));
    }
    return zhi.pop()=='t'?true:false;
    }
}
