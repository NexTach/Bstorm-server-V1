package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.auth.service.PasswordValidationService;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.PasswordChangeService;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final MemberRepository memberRepository;
    private final MemberAuthService memberAuthService;
    private final PasswordValidationService passwordValidationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void changePassword(HttpServletRequest request, String currentPassword, String password) {
        Members member = memberAuthService.getMemberByToken(request);
        passwordValidationService.validatePassword(member.getPassword(), currentPassword);
        password = bCryptPasswordEncoder.encode(password);
        member.setPassword(password);
        memberRepository.save(member);
    }
}