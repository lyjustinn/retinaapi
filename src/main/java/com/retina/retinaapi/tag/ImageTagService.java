package com.retina.retinaapi.tag;

import com.retina.retinaapi.aws.RekognitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.rekognition.model.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageTagService {

    private final ImageTagRepository imageTagRepository;

    private final RekognitionUtil rekognitionUtil;

    @Autowired
    public ImageTagService(ImageTagRepository imageTagRepository, RekognitionUtil rekognitionUtil) {
        this.imageTagRepository = imageTagRepository;
        this.rekognitionUtil = rekognitionUtil;
    }

    public List<ImageTag> getAllTags() {
        return this.imageTagRepository.findAll();
    }

    public void saveTag(ImageTag imageTag) {
        this.imageTagRepository.save(imageTag);
    }

    public void addTag(ImageTag imageTag) {
        Optional<ImageTag> exists = this.imageTagRepository.findByName(imageTag.getName());

        if (exists.isPresent()) return;

        this.imageTagRepository.save(imageTag);
    }

    public List<ImageTag> generateTags (MultipartFile file) {
        try {
            List<Label> labels = this.rekognitionUtil.getTags(file.getInputStream());
            List<String> stringLabels = new ArrayList<String>();

            for (Label label : labels) {
                stringLabels.add(label.name());
            }

            for (Label label : labels) {
                ImageTag newTag = new ImageTag();
                newTag.setName(label.name());

                this.addTag(newTag);
            }

            return this.imageTagRepository.findByNameIn(stringLabels);
        } catch (IOException e) {
            return null;
        }
    }
}
