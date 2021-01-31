package com.offcn;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.user.mapper")
public class ScwuserApplication {


    public static void main(String[] args) {
        SpringApplication.run(ScwuserApplication.class, args);
    }



}
