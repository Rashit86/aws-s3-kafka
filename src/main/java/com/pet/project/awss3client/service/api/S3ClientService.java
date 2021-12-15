package com.pet.project.awss3client.service.api;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface S3ClientService {

    /**
     * Get list of added or updated (last update) S3 objects
     * @return list of S3 objects
     * */
    List<S3Object> getS3Objects();

}
