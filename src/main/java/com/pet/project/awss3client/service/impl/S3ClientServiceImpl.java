package com.pet.project.awss3client.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.pet.project.awss3client.service.api.S3ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ClientServiceImpl implements S3ClientService {

    @Value("${aws-s3-kafka.aws.s3.credentials.region}")
    private String region;

    @Value("${aws-s3-kafka.aws.s3.credentials.access-key}")
    private String accessKey;

    @Value("${aws-s3-kafka.aws.s3.credentials.secret-key}")
    private String secretKey;

    @Value("${aws-s3-kafka.aws.s3.bucket-name}")
    private String bucketName;

    private static Map<String, Date> cars = new ConcurrentHashMap<>();

    @Override
    public List<S3Object> getS3Objects() {

        List<S3Object> s3Objects = new ArrayList<>();
        for (String key : getS3ObjectKeys()) {
            S3Object s3Object = getS3Object(key);
            Date lastModified = s3Object.getObjectMetadata().getLastModified();

            if (!cars.containsKey(key) || (cars.containsKey(key) && !cars.get(key).equals(lastModified))) {
                cars.put(key, lastModified);
                s3Objects.add(s3Object);
            }
        }
        return s3Objects;
    }

    private List<String> getS3ObjectKeys() {
        return getS3().listObjectsV2(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    private S3Object getS3Object(String key) {
        return getS3().getObject(bucketName, key);
    }

    private AmazonS3 getS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
