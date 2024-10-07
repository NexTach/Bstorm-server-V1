package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.enums.SignUpRequestRoles;
import com.nextech.server.v1.global.exception.EmailAlreadyExistsException;
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import com.nextech.server.v1.global.phonenumber.ConvertPhoneNumber;
import com.nextech.server.v1.global.relation.entity.Relation;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConvertPhoneNumber convertPhoneNumber;

    @Transactional
    @Override
    public SignUpResponse signUp(@Valid SignUpRequest signUpRequest) {
        String phoneNumber = convertPhoneNumber.convertPhoneNumber(signUpRequest.getPhoneNumber());
        checkIfPhoneNumberAlreadyExists(phoneNumber);
        Roles role = getRole(signUpRequest.getExtentOfDementia(), signUpRequest.getRole());
        Members newMember = createNewMember(signUpRequest, phoneNumber, role);
        Members savedMember = memberRepository.save(newMember);
        MembersInquiryListResponse membersInquiryListResponse = buildRelationListResponse(savedMember);
        return buildSignUpResponse(savedMember, membersInquiryListResponse);
    }

    private void checkIfPhoneNumberAlreadyExists(String phoneNumber) {
        if (memberRepository.findByPhoneNumber(phoneNumber) != null) {
            throw new EmailAlreadyExistsException("User is already subscribed");
        }
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

    private MembersInquiryListResponse buildRelationListResponse(Members member) {
        List<Relation> relations = relationRepository.findByFromProtected(member);
        if (relations.isEmpty()) {
            return new MembersInquiryListResponse(List.of());
        }

        if (member.getRole() == Roles.ROLE_PROTECTOR) {
            List<MembersInquiryResponse> wardMembers = relations.stream()
                    .flatMap(relation -> relation.getToWard().stream().map(this::buildMemberResponseByPhoneNumber))
                    .collect(Collectors.toList());
            return new MembersInquiryListResponse(wardMembers);
        } else if (Set.of(Roles.ROLE_WARD_0, Roles.ROLE_WARD_1, Roles.ROLE_WARD_2, Roles.ROLE_WARD_3).contains(member.getRole())) {
            Members protector = memberRepository.findByPhoneNumber(relations.get(0).getFromProtected().getPhoneNumber());
            return new MembersInquiryListResponse(List.of(buildMemberResponse(protector)));
        }
        return new MembersInquiryListResponse(List.of());
    }

    private MembersInquiryResponse buildMemberResponseByPhoneNumber(String phoneNumber) {
        Members member = memberRepository.findByPhoneNumber(phoneNumber);
        return buildMemberResponse(member);
    }

    private MembersInquiryResponse buildMemberResponse(Members member) {
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

    private SignUpResponse buildSignUpResponse(Members member, MembersInquiryListResponse membersInquiryListResponse) {
        if (member.getId() == null) {
            throw new IllegalStateException("Member ID is null");
        }
        return new SignUpResponse(
                member.getId(),
                member.getMemberName(),
                member.getPhoneNumber(),
                (short) member.getAge(),
                member.getGender(),
                member.getRole(),
                (short) member.getExtentOfDementia(),
                member.getProfilePictureURI(),
                membersInquiryListResponse
        );
    }
}
