package com.example.imageholder.aws.s3.impl;

import com.example.imageholder.aws.s3.properties.AwsS3Properties;
import com.example.imageholder.aws.s3.AwsS3BucketNameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsPropertyS3BucketNameProvider implements AwsS3BucketNameProvider {

    private final AwsS3Properties properties;

    @Autowired
    public AwsPropertyS3BucketNameProvider(AwsS3Properties properties) {
        this.properties = properties;
    }

    @Override
    public String provideS3BucketName() {
        return properties.getBucket();
    }
}
