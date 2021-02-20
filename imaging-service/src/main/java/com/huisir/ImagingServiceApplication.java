package com.huisir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImagingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagingServiceApplication.class, args);
    }
}
