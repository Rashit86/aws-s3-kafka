package com.pet.project.awss3client;

import com.pet.project.awss3client.consumer.KafkaConsumer;
import com.pet.project.awss3client.controller.api.S3KafkaService;
import com.pet.project.awss3client.service.api.S3ClientService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AwsS3ClientApplicationTests {

    @Value("${aws-s3-kafka.topic-name.s3-car}")
    private String s3CarTopicName;

    @Autowired
    private S3KafkaService s3KafkaService;

    @Autowired
    private KafkaConsumer consumer;

    @MockBean
    private S3ClientService s3ClientService;

    @Test
    @WithMockUser(username = "test", authorities = "write")
    void uploadToKafkaTest() throws Exception {

        Mockito.when(s3ClientService.getJsonS3Objects()).thenReturn(List.of("{\"carModel\" : \"carModel\"}"));
        s3KafkaService.unloadToKafka();

        CountDownLatch latch = consumer.getLatch();
        latch.await(10, TimeUnit.SECONDS);

        MatcherAssert.assertThat(consumer.getLatch().getCount(), equalTo(0L));
        MatcherAssert.assertThat(consumer.getPayload(), containsString(s3CarTopicName));
    }

}
