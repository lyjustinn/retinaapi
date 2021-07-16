package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;

import java.util.Set;

public class ImageTagDto {

    private String name;

    private Set<Image> images;

    public ImageTagDto(String name, Set<Image> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public Set<Image> getImages() {
        return images;
    }
}
