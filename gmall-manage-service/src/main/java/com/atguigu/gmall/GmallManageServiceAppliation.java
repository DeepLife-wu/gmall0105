package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.gmall.manage.mapper")
public class GmallManageServiceAppliation {

    public static void main(String[] args) {
        SpringApplication.run(GmallManageServiceAppliation.class, args);
    }
}
