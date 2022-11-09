package com.zime.ojdemo.cs;

import com.zime.ojdemo.untils.Io;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
public class cs {


    public static void main(String[] args) throws InterruptedException {
        Io.readPro(new File("C:/Users/26444/Desktop/data/1007"));
    }


}
