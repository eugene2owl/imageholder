package com.example.imageholder.images.service;

import com.example.imageholder.images.dto.ImageDownloadDto;
import com.example.imageholder.images.dto.ImageMetadataDto;
import com.example.imageholder.images.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    ImageDownloadDto getDownloadDtoById(Long id);

    Image getByName(String name);

    List<ImageMetadataDto> getAllMetadata();

    String create(MultipartFile multipartFile);
}
