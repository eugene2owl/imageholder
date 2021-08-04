package com.example.imageholder.images.service.impl;

import com.amazonaws.services.sqs.model.Message;
import com.example.imageholder.aws.sns.AwsSNSMessageBuilder;
import com.example.imageholder.aws.sns.AwsSNSService;
import com.example.imageholder.aws.sns.AwsSNSTopicArnProvider;
import com.example.imageholder.aws.sqs.AwsSQSService;
import com.example.imageholder.aws.sqs.impl.AwsSQSMessageBodyParser;
import com.example.imageholder.images.service.ImageEventNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "imageNotifier.scheduled.enabled", havingValue = "true")
public class ScheduledImageNotifier implements ImageEventNotificationService {

    private final AwsSQSService awsSQSService;
    private final AwsSNSService awsSNSService;
    private final AwsSNSMessageBuilder awsSNSMessageBuilder;
    private final AwsSNSTopicArnProvider awsSNSTopicArnProvider;
    private final AwsSQSMessageBodyParser awsSQSMessageBodyParser;

    public ScheduledImageNotifier(
            AwsSQSService awsSQSService,
            AwsSNSMessageBuilder awsSNSMessageBuilder,
            AwsSNSService awsSNSService,
            AwsSNSTopicArnProvider awsSNSTopicArnProvider,
            AwsSQSMessageBodyParser awsSQSMessageBodyParser
    ) {
        this.awsSQSService = awsSQSService;
        this.awsSNSMessageBuilder = awsSNSMessageBuilder;
        this.awsSNSService = awsSNSService;
        this.awsSNSTopicArnProvider = awsSNSTopicArnProvider;
        this.awsSQSMessageBodyParser = awsSQSMessageBodyParser;
    }

    /**
     * Notifies about newly uploaded to S3 bucket images.
     * Runs by schedule if enabled.
     * Event messages about newly uploaded images are read and deleted from SQS queue.
     * Notifications with images metadata are sent to the SNS topic.
     *
     * @return message with amount of processed messages.
     */
    @Scheduled(fixedDelayString = "${imageNotifier.scheduled.fixedDelay.in.milliseconds}")
    @Override
    public String notifyAboutNewlyUploadedImages() {
        var messages = awsSQSService.receiveMessagesFromQueue();
        if (!messages.isEmpty()) {
            publishNotifications(messages);
            awsSQSService.deleteMessagesFromQueue(messages);
        }
        return messages.size() + " SQS messages has been processed.";
    }

    private void publishNotifications(List<Message> messages) {
        var topicArn = awsSNSTopicArnProvider.provideSNSTopicArn();
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
