package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.relation.service.AllDeleteRelationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AllDeleteRelationServiceImpl implements AllDeleteRelationService {
    @Override
    public void deleteRelation(HttpServletRequest request) {
        // TODO: implement
    }
}
