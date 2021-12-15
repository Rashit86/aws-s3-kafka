package com.pet.project.awss3client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsS3ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsS3ClientApplication.class, args);
    }

}
