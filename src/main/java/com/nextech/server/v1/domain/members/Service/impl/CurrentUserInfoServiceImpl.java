package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.CurrentUserInfoService;
import com.nextech.server.v1.global.security.jwt.service.JwtAuthenticationService;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserInfoServiceImpl implements CurrentUserInfoService {

    private final MemberRepository memberRepository;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final JwtTokenService jwtTokenService;

    @Override
    public MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request) {
        String token = jwtTokenService.resolveToken(request.getHeader("Authorization"));
        Authentication userinfo = jwtAuthenticationService.getAuthentication(token);
        Members members = memberRepository.findByPhoneNumber(userinfo.getName());
        if (members == null) {
            throw new UsernameNotFoundException("User not found");
        }
        MembersInquiryResponse membersInquiryResponse = new MembersInquiryResponse(
                members.getId(),
                members.getMemberName(),
                (short) members.getAge(),
                members.getGender(),
                members.getRole(),
                members.getExtentOfDementia(),
                members.getProfilePictureURI()
        );
        return membersInquiryResponse;
    }
}