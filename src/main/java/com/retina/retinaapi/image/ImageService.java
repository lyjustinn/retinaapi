package com.retina.retinaapi.image;

import com.retina.retinaapi.tag.ImageTag;
import com.retina.retinaapi.tag.ImageTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final ImageTagService imageTagService;

    @Autowired
    public ImageService(ImageRepository imageRepository, ImageTagService imageTagService) {
        this.imageRepository = imageRepository;
        this.imageTagService = imageTagService;
    }

    public List<Image> getAllImages () {
        return this.imageRepository.findAll();
    }

    public Image getImage (Long id) {
        return this.imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image with id " + id + " DNE"));
    }

    public void addImage (Image image) {
        // will use a dto in the future

        // may need to do some mapping or AWS interactions in the future

        // need to generate tags as well

        ImageTag t1 = new ImageTag("tag1");

        t1.getImages().add(image);
        image.getTags().add(t1);

        this.imageTagService.addTag(t1);
        this.imageRepository.save(image);
    }

    public void addTagToImage (Long imageId, Long tagId) {

    }

}
