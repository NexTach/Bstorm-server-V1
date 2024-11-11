package com.nextech.server.v1.global.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final AmazonS3 s3Client;
    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    @Async
    public CompletableFuture<Void> deleteFile(String fileUrl) {
        String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
        String fileName = decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(bucket, fileName);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> deleteFile(String fileName, String bucket) {
        s3Client.deleteObject(bucket, fileName);
        return CompletableFuture.completedFuture(null);
    }
}