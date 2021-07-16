package com.example.imageholder.images.dto;

import com.example.imageholder.common.Dto;
import org.springframework.core.io.Resource;

public class ImageDownloadDto implements Dto {
    private Resource body;
    private String name;
    private String contentType;

    public Resource getBody() {
        return body;
    }

    public void setBody(Resource body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
