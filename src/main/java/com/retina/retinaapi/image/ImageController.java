package com.retina.retinaapi.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retina.retinaapi.aws.S3Util;
import com.retina.retinaapi.mapper.ImageDto;
import com.retina.retinaapi.mapper.ImageUpdateDto;
import com.retina.retinaapi.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping( path = "api/image")
public class ImageController {

    private final ImageService imageService;

    private final JwtUtilities jwtUtilities;

    @Autowired
    public ImageController(ImageService imageService, JwtUtilities jwtUtilities) {
        this.imageService = imageService;
        this.jwtUtilities = jwtUtilities;
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
    public ResponseEntity<?> addImage(@RequestHeader("Authorization") String authheader, @RequestParam("file") MultipartFile file,
                         @RequestParam("imageData") String imageData) {

        final String token = authheader.substring(7);
        final String username = this.jwtUtilities.extractUsername(token);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ImageDto imageDto = mapper.readValue(imageData, ImageDto.class);
            this.imageService.addImage(imageDto, file, username);

            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            System.out.println(e);
            return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = "{imageId}")
    public ResponseEntity<?> updateImage(@RequestBody ImageUpdateDto imageUpdateDto) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
