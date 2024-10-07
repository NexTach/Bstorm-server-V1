package com.nextech.server.v1.domain.members.controller;


import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse;
import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse;
import com.nextech.server.v1.domain.members.service.AllMembersInquiryService;
import com.nextech.server.v1.domain.members.service.CurrentUserInfoService;
import com.nextech.server.v1.domain.members.service.ParticularMemberInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계정", description = "계정관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MembersController {

    private final AllMembersInquiryService allMembersInquiryService;
    private final ParticularMemberInquiryService particularMemberInquiryService;
    private final CurrentUserInfoService currentUserInfoService;

    @Operation(summary = "AllMembersInquiry", description = "모든 회원 조회")
    @GetMapping("/all")
    public MembersInquiryListResponse getAllMembers() {
        return allMembersInquiryService.getAllMembers();
    }

    @Operation(summary = "ParticularMembersInquiry", description = "특정 회원 조회")
    @GetMapping("/{id}")
    public MembersInquiryResponse getParticularMembers(@PathVariable Long id) {
        return particularMemberInquiryService.getParticularMembers(id);
    }

    @Operation(summary = "CurrentUserInfo", description = "현재 로그인한 사용자 정보 조회")
    @GetMapping
    public MembersInquiryResponse getCurrentUserInfo(HttpServletRequest request) {
        return currentUserInfoService.getCurrentUserInfo(request);
    }
}