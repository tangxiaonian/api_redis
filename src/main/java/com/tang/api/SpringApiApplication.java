package com.tang.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname SpringApiApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/2/13 14:56
 * @Created by ASUS
 */
@MapperScan("com.tang.api.mapper")
@SpringBootApplication
public class SpringApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringApiApplication.class, args);
    }
}