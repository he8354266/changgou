package com.changgou;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableRabbit
@ComponentScan
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.business.dao"})
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run( BusinessApplication.class);
    }
}
