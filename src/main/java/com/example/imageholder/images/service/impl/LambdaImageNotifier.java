package com.example.imageholder.images.service.impl;

import com.example.imageholder.aws.lamdba.AwsLambdaFunctionArnProvider;
import com.example.imageholder.aws.lamdba.AwsLambdaService;
import com.example.imageholder.images.service.ImageEventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LambdaImageNotifier implements ImageEventNotificationService {

    private final AwsLambdaService awsLambdaService;
    private final AwsLambdaFunctionArnProvider awsLambdaFunctionArnProvider;

    @Autowired
    public LambdaImageNotifier(
            AwsLambdaService awsLambdaService,
            AwsLambdaFunctionArnProvider awsLambdaFunctionArnProvider
    ) {
        this.awsLambdaService = awsLambdaService;
        this.awsLambdaFunctionArnProvider = awsLambdaFunctionArnProvider;
    }

    @Override
    public String notifyAboutNewlyUploadedImages() {
        var functionArn = awsLambdaFunctionArnProvider.provideLambdaFunctionArn();
        return awsLambdaService.invokeFunction(null, functionArn);
    }
}
