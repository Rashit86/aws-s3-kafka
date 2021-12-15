package com.pet.project.awss3client.controller.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.pet.project.awss3client.controller.api.S3KafkaService;
import com.pet.project.awss3client.service.api.KafkaService;
import com.pet.project.awss3client.service.api.S3ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class S3KafkaServiceImpl implements S3KafkaService {

    private final S3ClientService s3ClientService;
    private final KafkaService kafkaService;

    @Scheduled(cron = "${aws-s3-kafka.profile.cron}")
    @Override
    public void unloadToKafka() throws IOException {

        for (S3Object s3Object : s3ClientService.getS3Objects()) {
            String json = StreamUtils.copyToString(s3Object.getObjectContent(), StandardCharsets.UTF_8);
            kafkaService.sendToTopic(json);
        }
    }
}
