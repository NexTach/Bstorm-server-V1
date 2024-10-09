package com.nextech.server.v1.global.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final AmazonS3 s3Client;
    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    public void deleteFile(String fileUrl) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
        String fileName = decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(bucket, fileName);
    }

    public void deleteFile(String fileName, String bucket) throws UnsupportedEncodingException {
        s3Client.deleteObject(bucket, fileName);
    }
}