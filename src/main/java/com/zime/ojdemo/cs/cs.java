package com.zime.ojdemo.cs;

import com.zime.ojdemo.untils.Io;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class cs {
    static int b;
    public static void main(String[] args) throws InterruptedException {
    System.out.println(LocalDateTime.now());
    }
}
