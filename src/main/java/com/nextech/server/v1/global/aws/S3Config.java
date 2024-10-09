package com.nextech.server.v1.global.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${AWS_S3_ENDPOINT_URL}")
    private String s3EndpointUrl;
    @Value("${AWS_S3_REGION}")
    private String s3Region;
    @Value("${AWS_ACCESS_KEY_ID}")
    private String awsAccessKey;
    @Value("${AWS_SECRET_KEY}")
    private String awsSecretKey;

    @Bean
    public AmazonS3 S3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndpointUrl, s3Region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}