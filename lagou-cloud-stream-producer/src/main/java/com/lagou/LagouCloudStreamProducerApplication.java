package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LagouCloudStreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouCloudStreamProducerApplication.class, args);
    }

}
