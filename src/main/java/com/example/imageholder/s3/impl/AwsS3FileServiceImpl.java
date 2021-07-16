package com.example.imageholder.s3.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.imageholder.s3.AwsS3FileService;
import com.example.imageholder.s3.S3BucketNameProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.util.UUID.randomUUID;

@Slf4j
@Service
public class AwsS3FileServiceImpl implements AwsS3FileService {

    private static final String AWS_KEY_SPECIAL_CHARACTERS = "[+]";
    private static final String AWS_BUCKET_FOLDER_NAME = "folder-for-images";
    private static final String KEY_SPECIAL_CHARACTER_REPLACEMENT = "_";
    private static final String ORIGINAL_FILE_NAME_SEPARATOR = "_";
    private static final String SEPARATOR_BETWEEN_FOLDERS = File.separator;

    private AmazonS3 awsS3Client;
    private S3BucketNameProvider s3BucketNameProvider;

    @Autowired
    public AwsS3FileServiceImpl(AmazonS3 awsS3Client, S3BucketNameProvider s3BucketNameProvider) {
        this.awsS3Client = awsS3Client;
        this.s3BucketNameProvider = s3BucketNameProvider;
    }

    @Override
    public String saveFileInS3(MultipartFile multipartFile, String fileName) {
        var encodedFileName = encodeFileName(fileName);
        var awsS3FilePath = generateS3FilePath(fileName);

        saveFileInS3(awsS3FilePath, multipartFile, encodedFileName);

        return awsS3FilePath;
    }

    @Override
    public Resource download(String s3FilePath) {
        try {
            return new InputStreamResource(
                    awsS3Client.getObject(s3BucketNameProvider.provideS3BucketName(), s3FilePath).getObjectContent()
            );
        } catch (AmazonClientException amazonClientException) {
            log.error("Error downloading file from aws s3 bucket", amazonClientException);
            throw amazonClientException;
        }
    }

    @Async
    void saveFileInS3(String awsS3FilePath, MultipartFile multipartFile, String encodedFileName) {
        try {
            saveFileInS3(awsS3FilePath, multipartFile.getInputStream(), multipartFile.getContentType(), encodedFileName);
        } catch (IOException ioException) {
            log.error("Error converting multipart file to file", ioException);
            throw new RuntimeException(ioException.getMessage());
        }
    }

    @Async
    void saveFileInS3(
            String awsS3FilePath,
            InputStream inputStream,
            String contentType,
            String fileName
    ) {
        try {
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentDisposition("attachment; filename=".concat(fileName));
            objectMetadata.setContentType(contentType);
            awsS3Client.putObject(
                    s3BucketNameProvider.provideS3BucketName(),
                    awsS3FilePath,
                    inputStream,
                    objectMetadata
            );
        } catch (AmazonClientException amazonClientException) {
            log.error("Error saving file to aws s3 bucket", amazonClientException);
            throw amazonClientException;
        }
    }

    private String encodeFileName(String fileName) {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    private String generateS3FilePath(String fileName) {
        return AWS_BUCKET_FOLDER_NAME
                .concat(SEPARATOR_BETWEEN_FOLDERS)
                .concat(randomUUID().toString())
                .concat(ORIGINAL_FILE_NAME_SEPARATOR)
                .concat(fileName.replace(AWS_KEY_SPECIAL_CHARACTERS, KEY_SPECIAL_CHARACTER_REPLACEMENT));
    }
}
