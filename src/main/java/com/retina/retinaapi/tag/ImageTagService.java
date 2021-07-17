package com.retina.retinaapi.tag;

import com.retina.retinaapi.aws.RekognitionUtil;
import com.retina.retinaapi.mapper.ImageTagDto;
import com.retina.retinaapi.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageTagService {

    private final ImageTagRepository imageTagRepository;

    private final RekognitionUtil rekognitionUtil;

    private final Mapper mapper;

    @Autowired
    public ImageTagService(ImageTagRepository imageTagRepository, RekognitionUtil rekognitionUtil, Mapper mapper) {
        this.imageTagRepository = imageTagRepository;
        this.rekognitionUtil = rekognitionUtil;
        this.mapper = mapper;
    }

    public List<ImageTagDto> getAllTags() {

        return this.mapper.mapImageTagDtos(this.imageTagRepository.findAll());
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

    public ImageTagDto getImageTag (Long id) {
        Optional<ImageTag> exists = this.imageTagRepository.findById(id);

        if (exists.isEmpty()) return null;

        return this.mapper.mapImageTagDto(exists.get());
    }

    public ImageTagDto getImageTag (String name) {
        Optional<ImageTag> exists = this.imageTagRepository.findByName(name);

        if (exists.isEmpty()) return null;

        return this.mapper.mapImageTagDto(exists.get());
    }

    public List<ImageTagDto> getRandomImageTags(int amount) {
        return this.mapper.mapImageTagDtos(this.imageTagRepository.findRandomImageTags(amount));
    }

    public void deleteTag (ImageTag tag) {
        System.out.println("man");
        this.imageTagRepository.deleteById(tag.getId());
    }

    public void deleteTags(List<Long> tags) {
        this.imageTagRepository.deleteAllById(tags);
    }
}
