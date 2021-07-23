package com.example.imageholder.images.service.impl;

import com.example.imageholder.images.dto.ImageDownloadDto;
import com.example.imageholder.images.dto.ImageMetadataDto;
import com.example.imageholder.images.dto.transformer.ImageDownloadTransformer;
import com.example.imageholder.images.dto.transformer.ImageMetadataTransformer;
import com.example.imageholder.images.repository.ImageRepository;
import com.example.imageholder.images.service.ImageService;
import com.example.imageholder.model.Image;
import com.example.imageholder.aws.s3.AwsS3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private AwsS3FileService awsS3FileService;
    private ImageRepository repository;
    private ImageMetadataTransformer metadataTransformer;
    private ImageDownloadTransformer downloadTransformer;

    @Autowired
    public ImageServiceImpl(
            AwsS3FileService awsS3FileService,
            ImageRepository repository,
            ImageMetadataTransformer metadataTransformer,
            ImageDownloadTransformer downloadTransformer
    ) {
        this.awsS3FileService = awsS3FileService;
        this.repository = repository;
        this.metadataTransformer = metadataTransformer;
        this.downloadTransformer = downloadTransformer;
    }

    @Override
    public ImageDownloadDto getById(Long id) {
        var image = findImageOrThrow(id);
        var body = awsS3FileService.download(image.getName());
        return downloadTransformer.transform(image, body);
    }

    @Override
    public List<ImageMetadataDto> getAllMetadata() {
        return repository.findAll().stream().map(metadataTransformer::transform).collect(Collectors.toList());
    }

    @Override
    public String create(MultipartFile multipartFile) {
        var pathInBucket = awsS3FileService.saveFileInS3(multipartFile, multipartFile.getOriginalFilename());
        var image = buildImage(pathInBucket, multipartFile);
        repository.save(image);
        return pathInBucket;
    }

    private Image buildImage(String pathInBucket, MultipartFile multipartFile) {
        var image = new Image();
        image.setName(pathInBucket);
        image.setSize(multipartFile.getSize());
        image.setExtension(multipartFile.getContentType());
        return image;
    }

    private Image findImageOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Image not found by id " + id));
    }
}
