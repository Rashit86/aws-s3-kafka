package com.pet.project.awss3client.service.api;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public interface S3ClientService {

    /**
     * Get list of jsons of added or updated (last update) S3 objects
     * @return list of jsons of S3 objects
     * */
    List<String> getJsonS3Objects() throws IOException;

}
