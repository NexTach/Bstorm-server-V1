package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.relation.dto.request.DeleteRelationRequest;
import com.nextech.server.v1.domain.relation.service.DeleteRelationService;
import com.nextech.server.v1.global.exception.RelationNullException;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteRelationServiceImpl implements DeleteRelationService {

    private static final Logger log = LoggerFactory.getLogger(DeleteRelationServiceImpl.class);
    private final ConvertPhoneNumber convertPhoneNumber;
    private final MemberAuthService memberAuthService;
    private final RelationRepository relationRepository;

    @Override
    public void deleteRelation(HttpServletRequest request, DeleteRelationRequest deleteRelationRequest) {
        Members member = memberAuthService.getMemberByToken(request);
        for (String ward : deleteRelationRequest.getDeleteWard()) {
            String convertWard = convertPhoneNumber.convertPhoneNumber(ward);
            List<Relation> relations = validateRelation(member, convertWard);
            for (Relation relation : relations) {
                relation.getToWard().remove(convertWard);
                relationRepository.save(relation);
            }
        }
    }

    private List<Relation> validateRelation(Members member, String ward) {
        log.info(ward);
        List<Relation> relations = relationRepository.findByFromProtectedAndToWardContains(member, ward);
        if (relations.isEmpty()) {
            throw new RelationNullException("Relation does not exist.");
        }
        return relations;
    }
}