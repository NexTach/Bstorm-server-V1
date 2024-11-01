package com.nextech.server.v1.domain.relation.dto.request

import jakarta.validation.constraints.NotNull

data class DeleteRelationRequest(
    @field:NotNull val deleteWard: List<String>
)