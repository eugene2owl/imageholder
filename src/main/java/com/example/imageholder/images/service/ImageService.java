package com.example.imageholder.images.service;

import com.example.imageholder.images.dto.ImageDownloadDto;
import com.example.imageholder.images.dto.ImageMetadataDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    ImageDownloadDto getById(Long id);

    List<ImageMetadataDto> getAllMetadata();

    String create(MultipartFile multipartFile);
}
