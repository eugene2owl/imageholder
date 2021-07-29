package com.example.imageholder.aws.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aws")
public class AwsProperties {

    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }
}
