package com.retina.retinaapi.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class S3Util {

    @Value("${aws.bucket.name}")
    private String bucket;

    private final S3Client s3Client;

    public S3Util() {
        Region region = Region.US_EAST_1;
        this.s3Client = S3Client.builder()
                .region(region)
                .build();
    }

    public void putObject(InputStream stream, String name, Long size) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("x-amz-meta-myVal", "test");

        System.out.println(this.bucket);

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(this.bucket)
                .key(name)
                .metadata(metadata)
                .build();

        System.out.println(name);

        PutObjectResponse res = s3Client.putObject(putOb,
                RequestBody.fromInputStream(stream, size));

    }
}
