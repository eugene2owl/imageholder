package com.example.imageholder.images.dto.transformer;

import com.example.imageholder.images.dto.ImageDownloadDto;
import com.example.imageholder.model.Image;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ImageDownloadTransformer {

    public ImageDownloadDto transform(Image source, Resource body) {
        var result = new ImageDownloadDto();
        result.setBody(body);
        result.setName(source.getName());
        result.setContentType(source.getExtension());
        return result;
    }
}
