package com.nextech.server.v1.domain.members.service;

import com.nextech.server.v1.domain.members.dto.response.PatchProfilePictureResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PatchProfilePictureService {
    ResponseEntity<PatchProfilePictureResponse> patchProfilePicture(HttpServletRequest request, MultipartFile file);
}