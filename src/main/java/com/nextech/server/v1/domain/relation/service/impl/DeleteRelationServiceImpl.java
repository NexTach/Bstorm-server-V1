package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.domain.relation.dto.request.DeleteRelationRequest;
import com.nextech.server.v1.domain.relation.service.DeleteRelationService;
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
public class DeleteRelationServiceImpl implements DeleteRelationService {

    private final MemberAuthService memberAuthService;
    private final RelationRepository relationRepository;

    @Override
    public void deleteRelation(HttpServletRequest request, DeleteRelationRequest deleteRelationRequest) {
        // Token을 통해 요청자 정보 얻기
        Members member = memberAuthService.getMemberByToken(request);

        // 삭제할 모든 피보호자 목록에 대해 관계를 검증하고 삭제
        for (String ward : deleteRelationRequest.getDeleteWard()) {
            validateRelation(member, ward); // 검증을 수행
            relationRepository.deleteByFromProtectedAndToWardContains(member, ward); // 관계 삭제
        }
    }

    private void validateRelation(Members member, String ward) {
        // 요청자가 보호자 또는 해당 ward와의 관계에 연관되어 있는지 확인
        List<Relation> relations = relationRepository.findByFromProtectedAndToWardContains(member, ward);
        if (relations.isEmpty()) {
            throw new IllegalArgumentException("해당 관계가 존재하지 않거나 권한이 없습니다.");
        }
    }
}