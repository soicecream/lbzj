package com.zime.ojdemo.cs;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zime.ojdemo.entity.Solution;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@ConditionalOnClass
public class a{
    public static void main(String[] args) {
        String url="D:\\java1\\lbzj\\src\\main\\java";

        FastAutoGenerator.create("jdbc:mysql://43.143.121.162:3306/jol?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("wang") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(url); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zime.ojdemo") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\java1\\lbzj\\src\\main\\java\\com\\zime\\ojdemo\\mapper\\xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_role","sys_menu","sys_role_menu","sys_user_role") // 设置需要生成的表名
                            .addTablePrefix("sys_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
