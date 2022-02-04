package com.pet.project.awss3client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.project.awss3client.dto.CarDto;
import com.pet.project.awss3client.service.api.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${aws-s3-kafka.topic-name.s3-car}")
    private String s3CarTopic;

    @Override
    public void sendToTopic(String json) throws JsonProcessingException {
        logger.info("Sending json = '{}' to topic = '{}'", json, s3CarTopic);
        kafkaTemplate.send(s3CarTopic, objectMapper.readValue(json, CarDto.class).getCarModel(), json);
    }

}
