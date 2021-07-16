package com.example.imageholder.s3.properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties for common AWS parameters, such as region and access credentials.
 *
 * @author ikatlinsky
 * @since 11/1/17
 */
@Component
@ConfigurationProperties("aws")
public class AwsProperties {

    private String region;
    private Boolean multiAz;
    private String environmentName;
    private Credentials credentials;

    public String getAccessKey() {
        return credentials.getAccessKey();
    }

    public String getSecretKey() {
        return credentials.getSecretKey();
    }

    public AWSCredentials buildCredentials() {
        return credentials.getCredentials();
    }

    public AWSCredentialsProvider buildCredentialsProvider() {
        return new AWSStaticCredentialsProvider(buildCredentials());
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public Boolean getMultiAz() {
        return multiAz;
    }

    public void setMultiAz(final Boolean multiAz) {
        this.multiAz = multiAz;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(final String environmentName) {
        this.environmentName = environmentName;
    }

    public static class Credentials {

        private String accessKey;
        private String secretKey;

        public AWSCredentials getCredentials() {
            return new BasicAWSCredentials(accessKey, secretKey);
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(final String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(final String secretKey) {
            this.secretKey = secretKey;
        }
    }
}
