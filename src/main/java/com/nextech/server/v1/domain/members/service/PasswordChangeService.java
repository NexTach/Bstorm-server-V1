package com.nextech.server.v1.domain.members.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PasswordChangeService {
    void changePassword(HttpServletRequest request,String currentPassword, String password);
}