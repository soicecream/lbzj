package com.zime.ojdemo.cs;

import org.springframework.context.ApplicationContext;

public class Main {
    static  abstract class Human{
        protected abstract void sayHello();
    }

    static class Man extends Human{

        @Override
        protected void sayHello() {
            System.out.println("Hi,Man");
        }
    }

    static class Woman extends Human{

        @Override
        protected void sayHello() {
            System.out.println("Hi,Woman");
        }
    }

    public static void main(String[] args) {
     Human man=new Man();
     Human woman=new Woman();
     man.sayHello();
     woman.sayHello();
     man=new Woman();
     man.sayHello();
    }


}
