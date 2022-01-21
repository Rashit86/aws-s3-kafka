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

    @Value("${aws-s3-kafka.kafka.topic-name}")
    private String topicName;

    public static void main(String[] args) {
        SpringApplication.run(AwsS3ClientApplication.class, args);
    }

    @Bean
    NewTopic createS3CarTopic(){
        return TopicBuilder
                .name(topicName)
                .partitions(1)
                //TODO: если ставить больше 1 реплики, то топик не создается.
                // Видимо потому что у нас создан только один бутстрап сервер
                .replicas(1)
                .build();
    }

}
