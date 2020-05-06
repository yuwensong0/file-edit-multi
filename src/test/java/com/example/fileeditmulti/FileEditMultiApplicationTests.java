package com.example.fileeditmulti;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.util.UUID;

@SpringBootTest
class FileEditMultiApplicationTests {

    @Test
    void contextLoads() throws Exception{
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

}
