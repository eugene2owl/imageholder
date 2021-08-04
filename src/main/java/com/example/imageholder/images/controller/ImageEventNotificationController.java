package com.example.imageholder.images.controller;

import com.example.imageholder.images.service.ImageEventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/images", produces = APPLICATION_JSON_VALUE)
public class ImageEventNotificationController {

    private final ImageEventNotificationService service;

    @Autowired
    public ImageEventNotificationController(@Qualifier("lambdaImageNotifier") ImageEventNotificationService service) {
        this.service = service;
    }

    /**
     * Notifies about newly uploaded to AWS S3 bucket images.
     * The work on reading and deleting the messages from SQS queue
     * and sending appropriate notification to the SNS topic
     * is done by the invoked Lambda function.
     *
     * @return payload received as a response of the Lambda function.
     */
    @PostMapping("/event-notifications/uploaded")
    public String notifyAboutNewlyUploaded() {
        return service.notifyAboutNewlyUploadedImages();
    }
}
