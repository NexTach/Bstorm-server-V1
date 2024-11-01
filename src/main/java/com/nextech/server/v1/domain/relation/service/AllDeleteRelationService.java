package com.nextech.server.v1.domain.relation.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AllDeleteRelationService {
    void deleteRelation(HttpServletRequest request);
}