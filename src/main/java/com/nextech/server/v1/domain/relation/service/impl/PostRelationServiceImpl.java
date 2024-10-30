package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.relation.dto.request.PostRelationRequest;
import com.nextech.server.v1.domain.relation.service.PostRelationService;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostRelationServiceImpl implements PostRelationService {

    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;
    private final ConvertPhoneNumber convertPhoneNumber;

    @Override
    public Relation postRelation(PostRelationRequest postRelationRequest) {
        String fromProtector = convertPhoneNumber.convertPhoneNumber(postRelationRequest.getFromProtector());
        String toWard = convertPhoneNumber.convertPhoneNumber(postRelationRequest.getToWard());
        Members protector = memberRepository.findByPhoneNumber(fromProtector);
        if (protector == null) {
            throw new UsernameNotFoundException("Protector with the provided phone number does not exist.");
        }
        Members ward = memberRepository.findByPhoneNumber(toWard);
        if (ward == null) {
            throw new UsernameNotFoundException("Ward with the provided phone number does not exist.");
        }
        List<Relation> existingRelations = relationRepository.findByFromProtected(protector);
        boolean alreadyExists = false;
        for (Relation relation : existingRelations) {
            if (relation.getToWard().contains(toWard)) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            throw new IllegalArgumentException("The relationship with the specified ward already exists.");
        }
        Relation newRelation = new Relation(null, protector, List.of(toWard));
        return relationRepository.save(newRelation);
    }
} // TODO: 2021-09-07 1. 보호자-피보호자 저장 로직 최적화(보호자 중복 저장문제 해결)