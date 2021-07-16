package com.example.imageholder.s3.impl;

import com.example.imageholder.s3.properties.AwsS3Properties;
import com.example.imageholder.s3.S3BucketNameProvider;
import org.springframework.stereotype.Component;

@Component
public class PropertyS3BucketNameProvider implements S3BucketNameProvider {

    private AwsS3Properties properties;

    public PropertyS3BucketNameProvider(AwsS3Properties properties) {
        this.properties = properties;
    }

    @Override
    public String provideS3BucketName() {
        return properties.getBucketName();
    }
}
