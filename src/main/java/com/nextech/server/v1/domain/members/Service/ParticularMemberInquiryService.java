package com.nextech.server.v1.domain.members.service;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;

public interface ParticularMemberInquiryService {
    MembersInquiryResponse getParticularMembers(Long id);
}