package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.domain.members.entity.Members;
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
        checkIfEmailAlreadyExists(signUpRequest.getEmail());
        Members newMember = createNewMember(signUpRequest);
        Members savedMember = memberRepository.save(newMember);
        return buildSignUpResponse(savedMember);
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (memberRepository.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException("User is already subscribed");
        }
    }

    private Members createNewMember(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return new Members(
                null,
                request.getMemberName(),
                request.getEmail(),
                encodedPassword,
                request.getAge(),
                request.getGender(),
                request.getRole(),
                request.getExtentOfDementia(),
                null);
    }

    private SignUpResponse buildSignUpResponse(Members member) {
        if (member.getId() == null) {
            throw new IllegalStateException("Member ID is null");
        }
        return new SignUpResponse(
                member.getId(), member.getMemberName(), member.getEmail(),
                (short) member.getAge(),
                member.getGender(),
                member.getRole(),
                (short) member.getExtentOfDementia(),
                member.getProfilePictureURI());
    }
}
