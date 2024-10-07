package com.nextech.server.v1.domain.relation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Relation",description = "관계설정 관련 API")
@RequestMapping("/relation")
@RequiredArgsConstructor
public class RelationController {
}
