package com.example.imageholder.images.controller;

import com.example.imageholder.images.dto.ImageMetadataDto;
import com.example.imageholder.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/images", produces = APPLICATION_JSON_VALUE)
public class ImageMetadataController {

    private ImageService service;

    @Autowired
    public ImageMetadataController(ImageService service) {
        this.service = service;
    }

    /**
     * Return list of metadata of the files stored in the system.
     * File metadata is read from the database.
     *
     * @return list of files metadata.
     */
    @GetMapping("/metadata")
    public List<ImageMetadataDto> getAllMetadata() {
        return service.getAllMetadata();
    }
}
