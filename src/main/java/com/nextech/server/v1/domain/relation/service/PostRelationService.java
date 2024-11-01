package com.nextech.server.v1.domain.relation.service;

import com.nextech.server.v1.domain.relation.dto.request.PostRelationRequest;
import com.nextech.server.v1.global.relation.entity.Relation;
import jakarta.servlet.http.HttpServletRequest;

public interface PostRelationService {
    Relation postRelation(PostRelationRequest postRelationRequest);
}