package com.example.fileeditmulti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fileeditmulti.dao")
public class FileEditMultiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileEditMultiApplication.class, args);
    }

}
