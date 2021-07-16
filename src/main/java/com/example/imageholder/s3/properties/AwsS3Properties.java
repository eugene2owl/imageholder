package com.example.imageholder.s3.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aws.s3")
public class AwsS3Properties {

    private String bucket;

    public String getBucketName() {
        return bucket;
    }

    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }
}
