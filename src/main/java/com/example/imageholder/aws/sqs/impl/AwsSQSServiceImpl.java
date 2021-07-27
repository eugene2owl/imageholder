package com.example.imageholder.aws.sqs.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequest;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.imageholder.aws.sqs.AwsSQSQueueNameProvider;
import com.example.imageholder.aws.sqs.AwsSQSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        var queueName = awsSQSQueueNameProvider.provideSQSQueueName();
        var request = buildReceiveRequest(queueName);

        List<Message> messages = amazonSQSClient.receiveMessage(request).getMessages();

        log.info("Received " + messages.size() + " SQS messages: " + messages.toString());

        return messages;
    }

    @Override
    public void deleteMessagesFromQueue(List<Message> messages) {
        var queueName = awsSQSQueueNameProvider.provideSQSQueueName();
        var request = buildDeleteRequest(queueName, messages);

        var result = amazonSQSClient.deleteMessageBatch(request);

        log.info("Successfully deleted " + result.getSuccessful().size() + " SQS messages: " + result.getSuccessful());
        log.info("Failed to delete " + result.getFailed().size() + " SQS messages: " + result.getFailed());
    }

    private ReceiveMessageRequest buildReceiveRequest(String queueName) {
        return new ReceiveMessageRequest()
                .withMaxNumberOfMessages(10)
                .withQueueUrl(queueName)
                .withWaitTimeSeconds(5);
    }

    private DeleteMessageBatchRequest buildDeleteRequest(String queueName, List<Message> messages) {
        var entries = messages.stream().map(this::buildDeleteEntry).collect(Collectors.toList());
        return new DeleteMessageBatchRequest()
                .withQueueUrl(queueName)
                .withEntries(entries);
    }

    private DeleteMessageBatchRequestEntry buildDeleteEntry(Message message) {
        return new DeleteMessageBatchRequestEntry()
                .withId(message.getMessageId())
                .withReceiptHandle(message.getReceiptHandle());
    }
}
