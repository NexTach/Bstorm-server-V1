package com.nextech.server.v1.domain.members.service;

import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface CurrentWardMembersInquiryService {
    MembersInquiryListResponse getWardMembers(HttpServletRequest request);
}
