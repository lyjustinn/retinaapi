package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;
import com.retina.retinaapi.tag.ImageTag;
import com.retina.retinaapi.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {

    public Mapper() {
    }

    public User mapUser (UserDto userDto) {
        if (userDto == null) return null;
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getBio());
    }

    public Image mapImage (ImageDto imageDto, User owner, String resourceName) {
        if (imageDto == null || owner == null) return null;

        Image newImage = new Image(imageDto.getName(), imageDto.getDescription());
        newImage.setOwner(owner);

        return newImage;
    }
}
