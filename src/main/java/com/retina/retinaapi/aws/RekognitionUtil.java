package com.retina.retinaapi.aws;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.InputStream;
import java.util.List;

@Service
public class RekognitionUtil {

    private final RekognitionClient rekognitionClient;

    public RekognitionUtil() {
        this.rekognitionClient = RekognitionClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public List<Label> getTags (InputStream imageStream) throws RekognitionException {
        SdkBytes imageBytes = SdkBytes.fromInputStream(imageStream);

        Image detectImage = Image.builder()
                .bytes(imageBytes)
                .build();

        DetectLabelsRequest request = DetectLabelsRequest.builder()
                .image(detectImage)
                .maxLabels(10)
                .build();

        DetectLabelsResponse labelsResponse = rekognitionClient.detectLabels(request);

        return labelsResponse.labels();
    }
}
