package com.nextech.server.v1.global.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 s3Client;

    public Pair<String, String> uploadFile(MultipartFile file, String bucketName) throws IOException {
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
        return new Pair<>(fileUrl, fileName);
    }
}