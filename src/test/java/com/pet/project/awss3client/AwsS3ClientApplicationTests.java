package com.pet.project.awss3client;

import com.pet.project.awss3client.consumer.KafkaConsumer;
import com.pet.project.awss3client.service.api.KafkaService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ActiveProfiles("test")
class AwsS3ClientApplicationTests {

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaService kafkaService;

    @Test
    void givenEmbeddedKafkaBroker_whenSending_thenMessageReceived() throws Exception {
        kafkaService.sendToTopic("simple json");
        CountDownLatch latch = consumer.getLatch();
        latch.await(10, TimeUnit.SECONDS);

        MatcherAssert.assertThat(consumer.getLatch().getCount(), equalTo(0L));
        MatcherAssert.assertThat(consumer.getPayload(), containsString("embedded-test-topic"));
    }

}
