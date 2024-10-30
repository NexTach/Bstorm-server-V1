package com.nextech.server.v1.domain.relation.dto.request

import jakarta.validation.constraints.NotNull

data class PostRelationRequest(
    @field:NotNull val fromProtector: String,
    @field:NotNull val toWard: String
)