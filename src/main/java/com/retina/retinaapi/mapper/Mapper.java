package com.retina.retinaapi.mapper;

import com.retina.retinaapi.image.Image;
import com.retina.retinaapi.tag.ImageTag;
import com.retina.retinaapi.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Image newImage = new Image(imageDto.getName(), imageDto.getDescription(), resourceName);
        newImage.setOwner(owner);

        return newImage;
    }

    public ImageTagDto mapImageTagDto (ImageTag imageTag) {
        return new ImageTagDto(imageTag.getId(), imageTag.getName(), imageTag.getImages());
    }

    public List<ImageTagDto> mapImageTagDtos (List<ImageTag> imageTags) {
        List<ImageTagDto> imageTagDtos = new ArrayList<ImageTagDto>();

        for (ImageTag imageTag : imageTags) {
            imageTagDtos.add(this.mapImageTagDto(imageTag));
        }

        return imageTagDtos;
    }
}
