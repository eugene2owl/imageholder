package com.example.imageholder.images.service.impl;

import com.amazonaws.services.sqs.model.Message;
import com.example.imageholder.aws.sns.AwsSNSMessageBuilder;
import com.example.imageholder.aws.sns.AwsSNSService;
import com.example.imageholder.aws.sns.AwsSNSTopicNameProvider;
import com.example.imageholder.aws.sqs.AwsSQSService;
import com.example.imageholder.aws.sqs.impl.AwsSQSMessageBodyParser;
import com.example.imageholder.images.service.ImageNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ScheduledImageNotifier implements ImageNotifier {

    private final AwsSQSService awsSQSService;
    private final AwsSNSService awsSNSService;
    private final AwsSNSMessageBuilder awsSNSMessageBuilder;
    private final AwsSNSTopicNameProvider awsSNSTopicNameProvider;
    private final AwsSQSMessageBodyParser awsSQSMessageBodyParser;

    public ScheduledImageNotifier(
            AwsSQSService awsSQSService,
            AwsSNSMessageBuilder awsSNSMessageBuilder,
            AwsSNSService awsSNSService,
            AwsSNSTopicNameProvider awsSNSTopicNameProvider,
            AwsSQSMessageBodyParser awsSQSMessageBodyParser
    ) {
        this.awsSQSService = awsSQSService;
        this.awsSNSMessageBuilder = awsSNSMessageBuilder;
        this.awsSNSService = awsSNSService;
        this.awsSNSTopicNameProvider = awsSNSTopicNameProvider;
        this.awsSQSMessageBodyParser = awsSQSMessageBodyParser;
    }

    @Scheduled(fixedDelayString = "${quartz.sqs.fixedDelay.in.milliseconds}")
    @Override
    public void notifyAboutNewlyUploadedImages() {
        var messages = awsSQSService.receiveMessagesFromQueue();
        if (!messages.isEmpty()) {
            publishNotifications(messages);
            awsSQSService.deleteMessagesFromQueue(messages);
        }
    }

    private void publishNotifications(List<Message> messages) {
        var topicArn = awsSNSTopicNameProvider.provideSNSTopicArn();
        messages.forEach(message -> publishNotification(message, topicArn));
    }

    private void publishNotification(Message message, String topicArn) {
        var fileKey = awsSQSMessageBodyParser.extractFileKeyFromS3Message(message);
        log.info("Extracted image key: " + fileKey);

        var notification = awsSNSMessageBuilder.buildImageUploadedMessage(fileKey);
        log.info("Built notifications: " + notification);

        awsSNSService.publishMessage(notification, topicArn);
    }
}
