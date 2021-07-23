package com.example.imageholder.images.service.impl;

import com.example.imageholder.aws.sqs.AwsSQSService;
import com.example.imageholder.images.service.ImageNotifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledImageNotifier implements ImageNotifier {

    private final AwsSQSService awsSQSService;

    public ScheduledImageNotifier(
            AwsSQSService awsSQSService
    ) {
        this.awsSQSService = awsSQSService;
    }

    @Scheduled(fixedDelayString = "${quartz.sqs.fixedDelay.in.milliseconds}")
    @Override
    public void notifyAboutNewlyUploadedImages() {
        var messages = awsSQSService.receiveMessagesFromQueue();
    }
}
