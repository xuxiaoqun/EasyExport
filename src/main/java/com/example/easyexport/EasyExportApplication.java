package com.example.easyexport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.easyexport.mapper")
public class EasyExportApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExportApplication.class, args);
    }

}
