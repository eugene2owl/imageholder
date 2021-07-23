package com.example.imageholder.aws.sqs.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.example.imageholder.aws.sqs.AwsSQSQueueNameProvider;
import com.example.imageholder.aws.sqs.AwsSQSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AwsSQSServiceImpl implements AwsSQSService {

    private final AmazonSQS amazonSQSClient;
    private final AwsSQSQueueNameProvider awsSQSQueueNameProvider;

    @Autowired
    public AwsSQSServiceImpl(AmazonSQS amazonSQSClient, AwsSQSQueueNameProvider awsSQSQueueNameProvider) {
        this.amazonSQSClient = amazonSQSClient;
        this.awsSQSQueueNameProvider = awsSQSQueueNameProvider;
    }

    @Override
    public List<Message> receiveMessagesFromQueue() {
        List<Message> messages = amazonSQSClient.receiveMessage(
                awsSQSQueueNameProvider.provideSQSQueueName()
        ).getMessages();

        log.info("Received message from SQS queue: ".concat(messages.toString()));

        return messages;
    }
}
