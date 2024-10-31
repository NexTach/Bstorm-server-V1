package com.nextech.server.v1.domain.relation.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class PostRelationRequest(
    @field:NotNull val fromProtector: String,
    @field:NotNull @JsonProperty("newWard") val toWard: String
)