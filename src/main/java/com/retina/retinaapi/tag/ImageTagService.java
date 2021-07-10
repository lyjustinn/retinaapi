package com.retina.retinaapi.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTagService {

    private final ImageTagRepository imageTagRepository;

    @Autowired
    public ImageTagService(ImageTagRepository imageTagRepository) {
        this.imageTagRepository = imageTagRepository;
    }

    public List<ImageTag> getAllTags() {
        return this.imageTagRepository.findAll();
    }

    public void addTag(ImageTag imageTag) {
        this.imageTagRepository.save(imageTag);
    }
}
