package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;

import java.util.Set;

public class ImageTagDto {

    private Long id;

    private String name;

    private Set<Image> images;

    public ImageTagDto(Long id, String name, Set<Image> images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Set<Image> getImages() {
        return images;
    }
}
