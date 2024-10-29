package com.nextech.server.v1.domain.relation.controller;

import com.nextech.server.v1.domain.relation.dto.response.RelationInquiryListResponse;
import com.nextech.server.v1.domain.relation.service.GetRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Relation", description = "관계설정 관련 API")
@RequestMapping("/relation")
@RequiredArgsConstructor
public class RelationController {

    private final GetRelationService getRelationService;

    @Operation(summary = "GetRelation", description = "자신의 모든 관계을 조회합니다.")
    @GetMapping
    public RelationInquiryListResponse getRelation(HttpServletRequest request) {
        return getRelationService.getRelation(request);
    }
}