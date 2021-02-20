package com.gjy.boke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gjy.boke.dao")
public class                                                                                                                                                                                                                                                                                                            BokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BokeApplication.class, args);
    }

}
