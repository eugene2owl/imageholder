package com.example.imageholder.images.dto.transformer;

import com.example.imageholder.images.dto.ImageMetadataDto;
import com.example.imageholder.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMetadataTransformer {

    public ImageMetadataDto transform(Image source) {
        var result = new ImageMetadataDto();
        result.setId(source.getId());
        result.setUuid(source.getUuid());
        result.setName(source.getName());
        result.setLastUpdatedAt(source.getLastUpdatedAt());
        result.setSize(source.getSize());
        result.setExtension(source.getExtension());
        return result;
    }
}
