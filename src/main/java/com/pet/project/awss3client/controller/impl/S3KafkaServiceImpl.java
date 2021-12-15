package com.pet.project.awss3client.controller.impl;

import com.pet.project.awss3client.controller.api.S3KafkaService;
import com.pet.project.awss3client.service.api.KafkaService;
import com.pet.project.awss3client.service.api.S3ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3KafkaServiceImpl implements S3KafkaService {

    private final S3ClientService s3ClientService;
    private final KafkaService kafkaService;

    @Scheduled(cron = "${aws-s3-kafka.profile.cron}")
    @Override
    public void unloadToKafka() throws IOException {

        for (String jsonS3Object : s3ClientService.getJsonS3Objects()) {
            kafkaService.sendToTopic(jsonS3Object);
        }
    }
}
