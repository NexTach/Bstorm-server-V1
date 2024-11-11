package com.nextech.server.v1.global.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 s3Client;

    @Async
    public CompletableFuture<Pair<String, String>> uploadFile(MultipartFile file, String bucketName) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        ObjectMetadata fileObj = new ObjectMetadata();
        fileObj.setContentLength(file.getSize());
        s3Client.putObject(
                bucketName,
                fileName,
                file.getInputStream(),
                fileObj
        );
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        return CompletableFuture.completedFuture(new Pair<>(fileUrl, fileName));
    }
}