package com.example.imageholder.aws.sns;

public interface AwsSNSMessageBuilder {

    String buildImageUploadedMessage(String imageName);
}
