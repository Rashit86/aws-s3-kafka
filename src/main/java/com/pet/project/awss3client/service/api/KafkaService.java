package com.pet.project.awss3client.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;

@Controller
public interface KafkaService {

    /**
     * Send json to kafka topic
     * @param json json
     * */
    void sendToTopic(String json) throws JsonProcessingException;
}
