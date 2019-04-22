package com.galaxis.wcs.yanfeng;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YanfengApplication {

    public static void main(String[] args) {
        SpringApplication.run(YanfengApplication.class, args);
    }

}
