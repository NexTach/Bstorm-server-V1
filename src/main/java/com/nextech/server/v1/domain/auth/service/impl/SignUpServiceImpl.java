package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.enums.SignUpRequestRoles;
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
        String phoneNumber = convertPhoneNumber(signUpRequest.getPhoneNumber());
        checkIfPhoneNumberAlreadyExists(phoneNumber);
        Roles role = getRole(signUpRequest.getExtentOfDementia(), signUpRequest.getRole());
        Members newMember = createNewMember(signUpRequest, phoneNumber, role);
        Members savedMember = memberRepository.save(newMember);
        return buildSignUpResponse(savedMember);
    }

    private void checkIfPhoneNumberAlreadyExists(String email) {
        if (memberRepository.findByPhoneNumber(email) != null) {
            throw new EmailAlreadyExistsException("User is already subscribed");
        }
    }

    private String convertPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("010")) {
            return phoneNumber.replaceFirst("010", "+8210");
        }
        return phoneNumber;
    }

    private Members createNewMember(SignUpRequest request, String phoneNumber, Roles role) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return new Members(
                null,
                request.getMemberName(),
                phoneNumber,
                encodedPassword,
                request.getAge(),
                request.getGender(),
                role,
                request.getExtentOfDementia(),
                null
        );
    }

    private Roles getRole(Short extentOfDementia, SignUpRequestRoles role) {
        if (role != SignUpRequestRoles.ROLE_WARD) {
            return Roles.ROLE_PROTECTOR;
        }
        if (extentOfDementia >= 0 && extentOfDementia < 20) {
            return Roles.ROLE_WARD_0;
        } else if (extentOfDementia >= 20 && extentOfDementia < 40) {
            return Roles.ROLE_WARD_1;
        } else if (extentOfDementia >= 40 && extentOfDementia < 60) {
            return Roles.ROLE_WARD_2;
        }
        return Roles.ROLE_WARD_3;
    }

    private SignUpResponse buildSignUpResponse(Members member) {
        if (member.getId() == null) {
            throw new IllegalStateException("Member ID is null");
        }
        return new SignUpResponse(member.getId(), member.getMemberName(), member.getPhoneNumber(), (short) member.getAge(), member.getGender(), member.getRole(), (short) member.getExtentOfDementia(), member.getProfilePictureURI());
    }
}
