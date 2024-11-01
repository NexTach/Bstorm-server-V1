package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignInRequest;
import com.nextech.server.v1.domain.auth.service.PasswordValidationService;
import com.nextech.server.v1.domain.auth.service.SignInService;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final ConvertPhoneNumber convertPhoneNumber;
    private final PasswordValidationService passwordValidationService;

    @Override
    @Transactional
    public TokenResponse signIn(SignInRequest signInRequest) {
        Members member = memberRepository.findByPhoneNumber(convertPhoneNumber.convertPhoneNumber(signInRequest.getPhoneNumber()));
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        passwordValidationService.validatePassword(member.getPassword(), signInRequest.getPassword());
        return jwtTokenService.generateTokenDto(member.getPhoneNumber(), member.getRole());
    }
}