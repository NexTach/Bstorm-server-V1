package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignInRequest;
import com.nextech.server.v1.domain.auth.service.SignInService;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.exception.InvalidCredentialsException;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TokenResponse signIn(SignInRequest signInRequest) {
        Members member = memberRepository.findByPhoneNumber(signInRequest.getPhoneNumber());
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            throw new InvalidCredentialsException("Password does not match");
        }
        return jwtTokenService.generateTokenDto(member.getPhoneNumber(), member.getRole());
    }
}