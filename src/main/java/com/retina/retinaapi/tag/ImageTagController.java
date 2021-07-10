package com.retina.retinaapi.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<ImageTag>> getAllTags() {
        return ResponseEntity.ok(this.imageTagService.getAllTags());
    }
}
