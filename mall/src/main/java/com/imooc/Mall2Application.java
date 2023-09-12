package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages="com.imooc.mall.dao")
@SpringBootApplication
public class Mall2Application {

//
//    @Autowired
//    CategoryMapper categoryMapper;

    public static void main(String[] args) {
        SpringApplication.run(Mall2Application.class, args);
    }

}
