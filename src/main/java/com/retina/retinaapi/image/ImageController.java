package com.retina.retinaapi.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "api/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(path = "allImages")
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(this.imageService.getAllImages());
    }

    @GetMapping(path = "{imageId}" )
    public ResponseEntity<Image> getImageById(@PathVariable("imageId") Long imageId) {
        return ResponseEntity.ok(this.imageService.getImage(imageId));
    }

    @PostMapping()
    public void addImage(@RequestBody Image image) {
        // should be a dto in the future
        this.imageService.addImage(image);
    }
}
