package com.example.imageholder.aws.sqs.impl;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class AwsSQSMessageBodyParser {

    private final ObjectMapper objectMapper;

    @Autowired
    public AwsSQSMessageBodyParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String extractFileKeyFromS3Message(Message message) {
        try {
            var bodyAsMap = objectMapper.readValue(message.getBody(), HashMap.class);
            var records = (List<HashMap>) bodyAsMap.get("Records");
            var s3 = (HashMap) records.get(0).get("s3");
            var s3Object = (HashMap) s3.get("object");
            return s3Object.get("key").toString();
        } catch (JsonProcessingException e) {
            log.error("Error while parsing SQS message body: " + message.getBody());
            throw new RuntimeException(e.getMessage());
        } catch (NullPointerException e) {
            log.error("Error while iterating throw SQS message body keys: " + message.getBody());
            throw new RuntimeException(e.getMessage());
        }
    }
}
