package com.example.imageholder.aws.sns.impl;

import com.example.imageholder.aws.sns.AwsSNSMessageBuilder;
import com.example.imageholder.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsSNSMessageBuilderImpl implements AwsSNSMessageBuilder {

    private final ImageService imageService;

    @Autowired
    public AwsSNSMessageBuilderImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public String buildImageUploadedMessage(String imageName) {
        var image = imageService.getByName(imageName);
        var downloadEndpoint = "http://localhost:8080/images/";
        return buildImageUploadedText(
                image.getId().toString(),
                image.getSize().toString(),
                image.getName(),
                image.getExtension(),
                downloadEndpoint
        );
    }

    private String buildImageUploadedText(
            String id,
            String size,
            String name,
            String extension,
            String downloadEndpoint
    ) {
        return "The image has been uploaded to the 'ImageHolder' system.\n" +
                String.format("Name: '%s'. Size: '%s'. Extension: '%s'.\n", name, size, extension) +
                String.format("Link for download: %s%s.\n", downloadEndpoint, id);
    }
}
