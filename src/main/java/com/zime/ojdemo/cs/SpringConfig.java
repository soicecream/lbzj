package com.zime.ojdemo.cs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MyBean myBean(){
        return new MyBean();
    }
}
