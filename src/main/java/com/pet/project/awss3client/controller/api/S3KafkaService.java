package com.pet.project.awss3client.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(value = "S3 data processing service")
@RestController
public interface S3KafkaService {

    @ApiOperation(value = "Send data to kafka")
    @PostMapping("/")
    void unloadToKafka() throws IOException;
}
