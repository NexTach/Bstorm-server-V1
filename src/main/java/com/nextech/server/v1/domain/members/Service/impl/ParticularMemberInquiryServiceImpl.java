package com.nextech.server.v1.domain.members.service.impl;

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.domain.members.service.ParticularMemberInquiryService;
import com.nextech.server.v1.domain.members.entity.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticularMemberInquiryServiceImpl implements ParticularMemberInquiryService {

    private final MemberRepository memberRepository;

    @Override
    public MembersInquiryResponse getParticularMembers(Long id) {
        Members member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        MembersInquiryResponse membersInquiryResponse = new MembersInquiryResponse(
                member.getId(),
                member.getMemberName(),
                (short) member.getAge(),
                member.getGender(),
                member.getRole(),
                member.getExtentOfDementia(),
                member.getProfilePictureURI()
        );
        return membersInquiryResponse;
    }
}
