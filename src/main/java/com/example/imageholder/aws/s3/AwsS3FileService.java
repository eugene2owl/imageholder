package com.example.imageholder.aws.s3;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3FileService {

    String saveFileInS3(MultipartFile multipartFile, String fileName);

    Resource download(String s3FilePath);
}
