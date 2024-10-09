package com.nextech.server.v1.domain.members.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PhoneNumberChangeService {
    void changePhoneNumber(HttpServletRequest request, String currentPassword, String phoneNumber);
}