package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.auth.service.PasswordValidationService;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.RoleChangeService;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.DuplicateRoleAssignmentException;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class RoleChangeServiceImpl implements RoleChangeService {

    private final MemberAuthService memberAuthService;
    private final RelationRepository relationRepository;
    private final PasswordValidationService passwordValidationService;
    private final MemberRepository memberRepository;
    private static final EnumSet<Roles> WARD_ROLES = EnumSet.of(
            Roles.ROLE_WARD_0, Roles.ROLE_WARD_1, Roles.ROLE_WARD_2, Roles.ROLE_WARD_3
    );

    @Override
    @Transactional
    public void changeRole(HttpServletRequest request, String currentPassword, Roles role) {
        Members member = memberAuthService.getMemberByToken(request);
        passwordValidationService.validatePassword(member.getPassword(), currentPassword);
        if(member.getRole() == role) {
            throw new DuplicateRoleAssignmentException("Duplicate role assignment");
        }
        if (member.getRole() == Roles.ROLE_PROTECTOR && WARD_ROLES.contains(role)) {
            relationRepository.deleteByFromProtected(member);
        }
        else if(WARD_ROLES.contains(member.getRole()) && role == Roles.ROLE_PROTECTOR) {
            relationRepository.deleteByToWardContains(member.getPhoneNumber());
        }
        member.setRole(role);
        memberRepository.save(member);
    }
}