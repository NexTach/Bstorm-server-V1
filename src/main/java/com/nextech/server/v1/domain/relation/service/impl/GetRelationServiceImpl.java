package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.relation.dto.response.RelationInqueryResponse;
import com.nextech.server.v1.domain.relation.dto.response.RelationInquiryListResponse;
import com.nextech.server.v1.domain.relation.service.GetRelationService;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.RelationNullException;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetRelationServiceImpl implements GetRelationService {

    private final MemberAuthService memberAuthService;
    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;

    @Override
    public RelationInquiryListResponse getRelation(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        if (member.getRole() == Roles.ROLE_PROTECTOR) {
            List<Relation> relations = relationRepository.findByFromProtected(member);
            if (relations.isEmpty()) {
                throw new RelationNullException("No relation found for the protector");
            }
            List<RelationInqueryResponse> responses = relations.stream()
                    .map(relation -> new RelationInqueryResponse(
                            relation.getId(),
                            relation.getFromProtected(),
                            convertPhoneNumbersToMembers(relation.getToWard())
                    ))
                    .collect(Collectors.toList());
            return new RelationInquiryListResponse(responses);
        } else if (member.getRole() == Roles.ROLE_ADMIN || member.getRole() == Roles.ROLE_DEVELOPER) {
            List<Relation> relations = relationRepository.findByToWardContains(member.getPhoneNumber());
            if (relations.isEmpty()) {
                relations = relationRepository.findByFromProtected(member);
                if (relations.isEmpty()) {
                    throw new RelationNullException("No relation found for admin/developer");
                }
            }
            List<RelationInqueryResponse> responses = relations.stream()
                    .map(relation -> new RelationInqueryResponse(
                            relation.getId(),
                            relation.getFromProtected(),
                            convertPhoneNumbersToMembers(relation.getToWard())
                    ))
                    .collect(Collectors.toList());
            return new RelationInquiryListResponse(responses);
        } else {
            List<Relation> relations = relationRepository.findByToWardContains(member.getPhoneNumber());
            if (relations.isEmpty()) {
                throw new RelationNullException("No relation found for the ward");
            }
            List<RelationInqueryResponse> responses = relations.stream()
                    .map(relation -> new RelationInqueryResponse(
                            relation.getId(),
                            relation.getFromProtected(),
                            convertPhoneNumbersToMembers(relation.getToWard())
                    ))
                    .collect(Collectors.toList());
            return new RelationInquiryListResponse(responses);
        }
    }

    private List<Members> convertPhoneNumbersToMembers(List<String> phoneNumbers) {
        return phoneNumbers.stream()
                .map(phone -> {
                    Members ward = memberRepository.findByPhoneNumber(phone);
                    if (ward == null) {
                        throw new RelationNullException("No ward found for phone: " + phone);
                    }
                    return ward;
                })
                .collect(Collectors.toList());
    }
}