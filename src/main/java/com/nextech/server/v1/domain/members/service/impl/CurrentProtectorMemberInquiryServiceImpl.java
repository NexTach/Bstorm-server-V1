package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.CurrentProtectorMemberInquiryService;
import com.nextech.server.v1.global.exception.RelationNullException;
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
public class CurrentProtectorMemberInquiryServiceImpl implements CurrentProtectorMemberInquiryService {

    private final MemberAuthService memberAuthService;
    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    @Override
    public MembersInquiryResponse getCurrentProtectorMemberInfo(HttpServletRequest request) {
        Members wardMember = memberAuthService.getMemberByToken(request);
        List<Relation> relationsAsWard = relationRepository.findByToWardContains(wardMember.getPhoneNumber());
        if (relationsAsWard.isEmpty()) {
            throw new RelationNullException("No protector found for this ward.");
        }
        Members protector = relationsAsWard.get(0).getFromProtected();
        return new MembersInquiryResponse(
                protector.getId(),
                protector.getMemberName(),
                (short) protector.getAge(),
                protector.getGender(),
                protector.getRole(),
                protector.getExtentOfDementia(),
                protector.getProfilePictureURI(),
                null
        );
    }
}