package com.retina.retinaapi.aws;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class s3Util {

    private final S3Client s3Client;

    public s3Util() {
        Region region = Region.US_EAST_1;
        this.s3Client = S3Client.builder()
                .region(region)
                .build();
    }

    public void putObject(File newFile, String name, String bucket) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("x-amz-meta-myVal", "test");

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucket)
                .key(name)
                .metadata(metadata)
                .build();

        PutObjectResponse res = s3Client.putObject(putOb,
                RequestBody.fromFile(newFile));

    }
}
