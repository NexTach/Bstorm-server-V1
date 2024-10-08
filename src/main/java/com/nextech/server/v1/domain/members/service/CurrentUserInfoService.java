package com.nextech.server.v1.domain.members.service;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface CurrentUserInfoService {
    MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request);
}