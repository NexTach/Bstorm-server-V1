package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.CurrentUserInfoService;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import com.nextech.server.v1.global.security.jwt.service.JwtAuthenticationService;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentUserInfoServiceImpl implements CurrentUserInfoService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final JwtTokenService jwtTokenService;

    @Override
    public MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request) {
        String token = jwtTokenService.resolveToken(request.getHeader("Authorization"));
        Authentication userinfo = jwtAuthenticationService.getAuthentication(token);
        Members member = memberRepository.findByPhoneNumber(userinfo.getName());
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
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
                            })
                    ).collect(Collectors.toList());
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
        } else {
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
}