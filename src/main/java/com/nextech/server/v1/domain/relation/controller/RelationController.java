package com.nextech.server.v1.domain.relation.controller;

import com.nextech.server.v1.domain.relation.dto.request.PostRelationRequest;
import com.nextech.server.v1.domain.relation.dto.response.RelationInquiryListResponse;
import com.nextech.server.v1.domain.relation.service.GetRelationService;
import com.nextech.server.v1.domain.relation.service.PostRelationService;
import com.nextech.server.v1.global.relation.entity.Relation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Relation", description = "관계설정 관련 API")
@RequestMapping("/relation")
@RequiredArgsConstructor
public class RelationController {

    private final GetRelationService getRelationService;
    private final PostRelationService postRelationService;

    @Operation(summary = "GetRelation", description = "관계조회")
    @GetMapping
    public RelationInquiryListResponse getRelation(HttpServletRequest request) {
        return getRelationService.getRelation(request);
    }

    @Operation(summary = "PostRelation", description = "관계추가")
    @PostMapping
    public Relation postRelation(@RequestBody PostRelationRequest postRelationRequest) {
        return postRelationService.postRelation(postRelationRequest);
    }
}