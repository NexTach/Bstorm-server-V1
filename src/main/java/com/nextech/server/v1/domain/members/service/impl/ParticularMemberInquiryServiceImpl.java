package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.ParticularMemberInquiryService;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticularMemberInquiryServiceImpl implements ParticularMemberInquiryService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    @Override
    public MembersInquiryResponse getParticularMembers(Long id) {
        Members member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Relation> relations = relationRepository.findByFromProtected(member);
        if (!relations.isEmpty()) {
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
            return new MembersInquiryResponse(
                    member.getId(),
                    member.getMemberName(),
                    (short) member.getAge(),
                    member.getGender(),
                    member.getRole(),
                    member.getExtentOfDementia(),
                    member.getProfilePictureURI(),
                    new MembersInquiryListResponse(wardMembers)
            );
        }
        List<Relation> relationsAsWard = relationRepository.findByToWardContains(member.getPhoneNumber());
        if (!relationsAsWard.isEmpty()) {
            Members protector = relationsAsWard.get(0).getFromProtected();
            return new MembersInquiryResponse(
                    member.getId(),
                    member.getMemberName(),
                    (short) member.getAge(),
                    member.getGender(),
                    member.getRole(),
                    member.getExtentOfDementia(),
                    member.getProfilePictureURI(),
                    new MembersInquiryListResponse(List.of(new MembersInquiryResponse(
                            protector.getId(),
                            protector.getMemberName(),
                            (short) protector.getAge(),
                            protector.getGender(),
                            protector.getRole(),
                            protector.getExtentOfDementia(),
                            protector.getProfilePictureURI(),
                            null
                    )))
            );
        }
        return new MembersInquiryResponse(
                member.getId(),
                member.getMemberName(),
                (short) member.getAge(),
                member.getGender(),
                member.getRole(),
                member.getExtentOfDementia(),
                member.getProfilePictureURI(),
                null
        );
    }
}