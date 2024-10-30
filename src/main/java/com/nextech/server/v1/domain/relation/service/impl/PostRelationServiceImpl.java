package com.nextech.server.v1.domain.relation.service.impl;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.relation.dto.request.PostRelationRequest;
import com.nextech.server.v1.domain.relation.service.PostRelationService;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.InvalidRoleException;
import com.nextech.server.v1.global.exception.RelationAlreadyExistsException;
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
        if (protector.getRole() == Roles.ROLE_WARD_0 ||
                protector.getRole() == Roles.ROLE_WARD_1 ||
                protector.getRole() == Roles.ROLE_WARD_2 ||
                protector.getRole() == Roles.ROLE_WARD_3) {
            throw new InvalidRoleException("Protector cannot be a ward.");
        }
        Members ward = memberRepository.findByPhoneNumber(toWard);
        if (ward == null) {
            throw new UsernameNotFoundException("Ward with the provided phone number does not exist.");
        }
        if (ward.getRole() == Roles.ROLE_PROTECTOR) {
            throw new InvalidRoleException("Ward cannot be a protector.");
        }
        List<Relation> existingRelations = relationRepository.findByFromProtected(protector);
        if (!existingRelations.isEmpty()) {
            Relation existingRelation = existingRelations.get(0);
            if (existingRelation.getToWard().contains(toWard)) {
                throw new RelationAlreadyExistsException("Relation already exists.");
            }
            existingRelation.getToWard().add(toWard);
            return relationRepository.save(existingRelation);
        }
        Relation newRelation = new Relation(null, protector, List.of(toWard));
        return relationRepository.save(newRelation);
    }
}