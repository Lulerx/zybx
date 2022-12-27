package com.post.zybx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * create by Luler on 2022/12/26 9:16
 *
 * @description
 */
@SpringBootApplication
@MapperScan("com.post.zybx.mapper")
public class Main8086 {
    public static void main(String[] args) {
        SpringApplication.run(Main8086.class, args);
    }
}
