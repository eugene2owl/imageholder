package com.example.imageholder.aws.sqs;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;

public interface AwsSQSService {

    List<Message> receiveMessagesFromQueue();
}
