package com.retina.retinaapi.image;

import com.retina.retinaapi.aws.RekognitionUtil;
import com.retina.retinaapi.aws.S3Util;
import com.retina.retinaapi.mapper.ImageDto;
import com.retina.retinaapi.mapper.Mapper;
import com.retina.retinaapi.tag.ImageTag;
import com.retina.retinaapi.tag.ImageTagService;
import com.retina.retinaapi.user.User;
import com.retina.retinaapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class ImageService {

    private final RekognitionUtil rekognitionUtil;

    private final S3Util s3Util;

    private final Mapper mapper;

    private final ImageRepository imageRepository;

    private final ImageTagService imageTagService;

    private final UserRepository userRepository;

    @Autowired
    public ImageService(RekognitionUtil rekognitionUtil, S3Util s3Util, Mapper mapper, ImageRepository imageRepository,
                        ImageTagService imageTagService, UserRepository userRepository) {
        this.rekognitionUtil = rekognitionUtil;
        this.s3Util = s3Util;
        this.mapper = mapper;
        this.imageRepository = imageRepository;
        this.imageTagService = imageTagService;
        this.userRepository = userRepository;
    }

    public List<Image> getAllImages () {
        return this.imageRepository.findAll();
    }

    public Image getImage (Long id) {
        return this.imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image with id " + id + " DNE"));
    }

    public List<Image> getImageBySearch(String search) {
        return this.imageRepository.findImageBySearch(search);
    }

    public void addImage (ImageDto imageDto, MultipartFile file, String username) throws IOException{

        User currentUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IOException("User DNE"));

        String[] split = file.getOriginalFilename().split("[.]");
        String extension = split[split.length-1];
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd hh mm ss");
        Date utcNow = Date.from(Instant.now());
        String dateString = dateFormat.format(utcNow).replaceAll("\\s+","");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        final String resourceName = uuid + dateString + "." + extension;

        this.s3Util.putObject(file.getInputStream(), "users/" + resourceName, file.getSize());

        Image image = this.mapper.mapImage(imageDto, currentUser, resourceName);

        List<ImageTag> tags = this.imageTagService.generateTags(file);

        for (ImageTag tag : tags) {
            tag.getImages().add(image);
            image.getTags().add(tag);
        }

        this.imageRepository.save(image);

    }

    public void updateImage(ImageDto imageDto, String username, Long imageId) {
        Optional<Image> isImage = this.imageRepository.findById(imageId);

        if (!isImage.isPresent()) return;

        Image image = isImage.get();

        if (!image.getOwner().getUsername().equals(username)) return;

        if (imageDto.getName().length() > 0) {
            image.setName(imageDto.getName());
        }

        if (imageDto.getDescription().length() > 0) {
            image.setDescription(imageDto.getDescription());
        }

        this.imageRepository.save(image);
    }

    public void deleteImage(Long imageId, String username) throws Error {
        Optional<Image> isImage = this.imageRepository.findById(imageId);

        if (!isImage.isPresent()) throw new EntityNotFoundException("DNE");

        Image image = isImage.get();

        if (!image.getOwner().getUsername().equals(username)) throw new IllegalAccessError("not valid action");

        Set<ImageTag> tags = image.getTags();

        this.imageRepository.delete(image);
        s3Util.deleteObject("users/" + image.getOwner().getUsername() + "/" + image.getResourceName());

        List<Long> toDelete = new ArrayList<Long>();
        for (ImageTag tag : tags ) {
            if (tag.getImages().isEmpty()) toDelete.add(tag.getId());
        }

        this.imageTagService.deleteTags(toDelete);
    }
}
