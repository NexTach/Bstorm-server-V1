package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.relation.service.AllDeleteRelationService;
import com.nextech.server.v1.global.exception.RelationNullException;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AllDeleteRelationServiceImpl implements AllDeleteRelationService {

    private final MemberAuthService memberAuthService;
    private final RelationRepository relationRepository;

    @Override
    public void deleteRelation(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        List<Relation>relations = relationRepository.findByFromProtected(member);
        if (relations.isEmpty()) {
            throw new RelationNullException("Relation does not exist.");
        }
        relations.forEach(relation -> {
            relation.getToWard().clear();
            relationRepository.save(relation);
        });
    }
}