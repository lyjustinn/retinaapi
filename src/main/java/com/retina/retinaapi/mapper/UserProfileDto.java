package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;

import java.util.List;

public class UserProfileDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String name;

    private String bio;

    private List<Image> images;

    public UserProfileDto(Long id, String firstName, String lastName, String name, String bio, List<Image> images) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.bio = bio;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
