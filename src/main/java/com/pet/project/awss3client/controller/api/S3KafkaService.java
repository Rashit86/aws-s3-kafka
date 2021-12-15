package com.pet.project.awss3client.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public interface S3KafkaService {

    @PostMapping("/")
    void unloadToKafka() throws IOException;
}
