package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.auth.service.PasswordValidationService;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.PhoneNumberChangeService;
import com.nextech.server.v1.global.exception.PhoneNumberAlreadyExistsException;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PhoneNumberChangeServiceImpl implements PhoneNumberChangeService {

    private final MemberRepository memberRepository;
    private final MemberAuthService memberAuthService;
    private final ConvertPhoneNumber convertPhoneNumber;
    private final PasswordValidationService passwordValidationService;

    @Override
    @Transactional
    public void changePhoneNumber(HttpServletRequest request, String currentPassword, String phoneNumber) {
        Members member = memberAuthService.getMemberByToken(request);
        passwordValidationService.validatePassword(member.getPassword(), currentPassword);
        String convertedPhoneNumber = convertPhoneNumber.convertPhoneNumber(phoneNumber);
        if (memberRepository.findByPhoneNumber(convertedPhoneNumber) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        member.setPhoneNumber(convertedPhoneNumber);
        memberRepository.save(member);
    }
}