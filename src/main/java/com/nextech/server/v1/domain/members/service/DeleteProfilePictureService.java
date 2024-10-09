package com.nextech.server.v1.domain.members.service;

import jakarta.servlet.http.HttpServletRequest;

public interface DeleteProfilePictureService {
    void deleteProfilePicture(HttpServletRequest request);
}