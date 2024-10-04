package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.domain.members.entity.Member;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.exception.EmailAlreadyExistsException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public SignUpResponse signUp(@Valid SignUpRequest signUpRequest) {
        if (memberRepository.findByEmail(signUpRequest.getEmail()) != null) {
            throw new EmailAlreadyExistsException("User is already subscribed");
        }
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        Member member = new Member(null, signUpRequest.getMemberName(), signUpRequest.getEmail(), encodedPassword, signUpRequest.getAge(), signUpRequest.getGender(), signUpRequest.getRole(), signUpRequest.getExtentOfDementia(), null);
        memberRepository.save(member);
        member = memberRepository.findByEmail(signUpRequest.getEmail());
        if (member.getId() == null) {
            throw new IllegalStateException("Member ID is null");
        }
        return new SignUpResponse(member.getId(), member.getMemberName(), member.getEmail(), (short) member.getAge(), member.getGender(), member.getRole(), (short) member.getExtentOfDementia(), member.getProfilePictureURI());
    }
}