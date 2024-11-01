package com.nextech.server.v1.domain.relation.controller;

import com.nextech.server.v1.domain.relation.dto.request.DeleteRelationRequest;
import com.nextech.server.v1.domain.relation.dto.request.PostRelationRequest;
import com.nextech.server.v1.domain.relation.dto.response.RelationInquiryListResponse;
import com.nextech.server.v1.domain.relation.service.AllDeleteRelationService;
import com.nextech.server.v1.domain.relation.service.DeleteRelationService;
import com.nextech.server.v1.domain.relation.service.GetRelationService;
import com.nextech.server.v1.domain.relation.service.PostRelationService;
import com.nextech.server.v1.global.relation.entity.Relation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relation")
@RequiredArgsConstructor
public class RelationController {

    private final GetRelationService getRelationService;
    private final PostRelationService postRelationService;
    private final DeleteRelationService deleteRelationService;
    private final AllDeleteRelationService allDeleteRelationService;

    @GetMapping
    public RelationInquiryListResponse getRelation(HttpServletRequest request) {
        return getRelationService.getRelation(request);
    }

    @PostMapping
    public Relation postRelation(@RequestBody PostRelationRequest postRelationRequest) {
        return postRelationService.postRelation(postRelationRequest);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRelation(HttpServletRequest request, @RequestBody DeleteRelationRequest deleteRelationRequest) {
        deleteRelationService.deleteRelation(request, deleteRelationRequest);
    }

    @DeleteMapping("/all")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void allDeleteRelation(HttpServletRequest request) {
        allDeleteRelationService.deleteRelation(request);
    }
}