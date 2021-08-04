package com.example.imageholder.aws.lamdba.impl;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.ServiceException;
import com.example.imageholder.aws.lamdba.AwsLambdaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AwsLambdaServiceImpl implements AwsLambdaService {

    private final AWSLambda amazonLambdaClient;

    @Autowired
    public AwsLambdaServiceImpl(AWSLambda amazonLambdaClient) {
        this.amazonLambdaClient = amazonLambdaClient;
    }

    @Override
    public String invokeFunction(String payload, String functionArn) {
        try {
            var request = buildInvokeRequest(payload, functionArn);
            var result = amazonLambdaClient.invoke(request);
            var resultAsString = new String(result.getPayload().array(), StandardCharsets.UTF_8);

            log.info("Successfully executed lambda function. The response is: " + resultAsString);
            return resultAsString;
        } catch (ServiceException e) {
            log.error("Error executing lambda function: " + functionArn);
            throw e;
        }
    }

    private InvokeRequest buildInvokeRequest(String payload, String functionArn) {
        return new InvokeRequest()
                .withFunctionName(functionArn)
                .withPayload(payload);
    }
}
