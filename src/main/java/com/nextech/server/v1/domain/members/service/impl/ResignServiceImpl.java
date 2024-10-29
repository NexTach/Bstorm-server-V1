package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.ResignService;
import com.nextech.server.v1.global.members.service.MemberAuthService;
import com.nextech.server.v1.global.relation.repository.RelationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResignServiceImpl implements ResignService {

    private final MemberAuthService memberAuthService;
    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    @Override
    @Transactional
    public void resign(HttpServletRequest request) {
        Members member = memberAuthService.getMemberByToken(request);
        memberRepository.delete(member);
        relationRepository.deleteByToWardContains(member.getPhoneNumber());
    }
}
