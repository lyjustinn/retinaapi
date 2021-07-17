package com.retina.retinaapi.tag;

import com.retina.retinaapi.image.Image;
import com.retina.retinaapi.mapper.ImageTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( path = "api/tag")
public class ImageTagController {

    private final ImageTagService imageTagService;

    @Autowired
    public ImageTagController(ImageTagService imageTagService) {
        this.imageTagService = imageTagService;
    }

    @GetMapping()
    public ResponseEntity<List<ImageTagDto>> getAllTags() {
        return ResponseEntity.ok(this.imageTagService.getAllTags());
    }

    @GetMapping( path = "{tagId}")
    public ResponseEntity<?> getTag(@PathVariable("tagId") Long tagId) {
        ImageTagDto imageTagDto = this.imageTagService.getImageTag(tagId);

        if (imageTagDto == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(imageTagDto);
    }

    @GetMapping( path = "{tagName}")
    public ResponseEntity<?> getTagByName(@PathVariable("tagName") String tagName) {
        ImageTagDto imageTagDto = this.imageTagService.getImageTag(tagName);

        if (imageTagDto == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(imageTagDto);
    }

    @GetMapping( path = "/sample/{amount}")
    public ResponseEntity<List<ImageTagDto>> getTagSample(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(this.imageTagService.getRandomImageTags(amount));
    }
}
