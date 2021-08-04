package com.example.imageholder.aws.lamdba.impl;

import com.example.imageholder.aws.lamdba.AwsLambdaFunctionArnProvider;
import com.example.imageholder.aws.lamdba.properties.AwsLambdaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsPropertyLambdaFunctionArnProvider implements AwsLambdaFunctionArnProvider {

    private final AwsLambdaProperties properties;

    @Autowired
    public AwsPropertyLambdaFunctionArnProvider(AwsLambdaProperties properties) {
        this.properties = properties;
    }

    @Override
    public String provideLambdaFunctionArn() {
        return properties.getFunctionArn();
    }
}
