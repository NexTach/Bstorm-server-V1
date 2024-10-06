package com.nextech.server.v1.domain.members.controller;

import com.nextech.server.v1.domain.members.Service.AllMembersInquiryService;
import com.nextech.server.v1.domain.members.dto.response.AllMembersInquiryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계정", description = "계정관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MembersController {

    private final AllMembersInquiryService allMembersInquiryService;

    @Operation(summary = "AllMembersInquiry", description = "모든 회원 조회")
    @GetMapping("/all")
    public AllMembersInquiryResponse getAllMembers() {
        return new AllMembersInquiryResponse(allMembersInquiryService.getAllMembers());
    }
}