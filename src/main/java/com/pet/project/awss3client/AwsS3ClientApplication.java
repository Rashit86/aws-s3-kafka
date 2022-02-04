package com.pet.project.awss3client;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsS3ClientApplication {

    @Value("${aws-s3-kafka.topic-name.s3-car}")
    private String s3CarTopic;

    public static void main(String[] args) {
        SpringApplication.run(AwsS3ClientApplication.class, args);
    }

    @Bean
    NewTopic createS3CarTopic(){
        return TopicBuilder
                .name(s3CarTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
