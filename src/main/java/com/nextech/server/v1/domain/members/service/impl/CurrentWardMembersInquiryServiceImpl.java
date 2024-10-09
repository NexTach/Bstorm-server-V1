package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.CurrentWardMembersInquiryService;
import com.nextech.server.v1.global.exception.RelationNullException;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentWardMembersInquiryServiceImpl implements CurrentWardMembersInquiryService {

    private final MemberAuthService memberAuthService;
    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    @Override
    public MembersInquiryListResponse getWardMembers(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        List<Relation> relations = relationRepository.findByFromProtected(member);
        if (relations.isEmpty()) {
            throw new RelationNullException("No relations found for this protector");
        }
        List<MembersInquiryResponse> wardMembers = relations.stream()
                .flatMap(relation -> relation.getToWard().stream()
                        .map(phoneNumber -> {
                            Members wardMember = memberRepository.findByPhoneNumber(phoneNumber);
                            return new MembersInquiryResponse(
                                    wardMember.getId(),
                                    wardMember.getMemberName(),
                                    (short) wardMember.getAge(),
                                    wardMember.getGender(),
                                    wardMember.getRole(),
                                    wardMember.getExtentOfDementia(),
                                    wardMember.getProfilePictureURI(),
                                    null
                            );
                        }))
                .collect(Collectors.toList());
        return new MembersInquiryListResponse(wardMembers);
    }
}
