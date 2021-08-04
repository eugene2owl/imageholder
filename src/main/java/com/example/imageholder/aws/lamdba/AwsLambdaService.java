package com.example.imageholder.aws.lamdba;

public interface AwsLambdaService {

    String invokeFunction(String payload, String functionArn);
}
