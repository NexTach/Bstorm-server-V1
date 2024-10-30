package com.nextech.server.v1.domain.relation.service;

import com.nextech.server.v1.domain.relation.dto.request.DeleteRelationRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface DeleteRelationService {
    void deleteRelation(HttpServletRequest request, DeleteRelationRequest deleteRelationRequest);
}