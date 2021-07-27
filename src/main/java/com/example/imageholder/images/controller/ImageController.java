package com.example.imageholder.images.controller;

import com.example.imageholder.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/images", produces = APPLICATION_JSON_VALUE)
public class ImageController {

    private ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    /**
     * Return the file with provided id.
     * File metadata is read from the database.
     * File content is read from the AWS S3 bucket.
     *
     * @param id Id of the file.
     * @return File with body in form of {@link org.springframework.core.io.Resource}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getById(@PathVariable Long id) {
        var dto = service.getDownloadDtoById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getName() + "\"")
                .body(dto.getBody());
    }

    /**
     * Create the file.
     * File metadata is stored in the database.
     * File content is stored in the AWS S3 bucket.
     *
     * @param multipartFile File to save in the system.
     * @return Path to the file in the AWS S3 bucket.
     */
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public String create(@RequestPart MultipartFile multipartFile) {
        return service.create(multipartFile);
    }

}
