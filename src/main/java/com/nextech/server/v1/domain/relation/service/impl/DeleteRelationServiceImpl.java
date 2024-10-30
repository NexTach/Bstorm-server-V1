package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.relation.dto.request.DeleteRelationRequest;
import com.nextech.server.v1.domain.relation.service.DeleteRelationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRelationServiceImpl implements DeleteRelationService {
    @Override
    public void deleteRelation(HttpServletRequest request, DeleteRelationRequest deleteRelationRequest) {
        //TODO: implement
    }
}