package com.nextech.server.v1.domain.relation.service;

import com.nextech.server.v1.domain.relation.dto.response.RelationInquiryListResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface GetRelationService {
    RelationInquiryListResponse getRelation(HttpServletRequest request);
}