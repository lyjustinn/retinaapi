package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;

import java.util.List;

public class UserProfileDto {

    private Long id;

    private String name;

    private String bio;

    private List<Image> images;

    public UserProfileDto(Long id, String name, String bio, List<Image> images) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public List<Image> getImages() {
        return images;
    }
}
