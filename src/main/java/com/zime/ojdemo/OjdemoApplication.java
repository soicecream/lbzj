package com.zime.ojdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OjdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjdemoApplication.class, args);
    }

}
