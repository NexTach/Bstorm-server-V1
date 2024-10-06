package com.nextech.server.v1.domain.members.Service.impl;

import com.nextech.server.v1.domain.members.Service.AllMembersInquiryService;
import com.nextech.server.v1.domain.members.dto.response.AllMembersInquiryResponse;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllMembersInquiryServiceImpl implements AllMembersInquiryService {

    private final MemberRepository memberRepository;

    @Override
    public List<Members> getAllMembers() {
        return memberRepository.findAll();
    }
}
