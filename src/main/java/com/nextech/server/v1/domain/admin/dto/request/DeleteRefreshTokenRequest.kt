package com.nextech.server.v1.domain.admin.dto.request

import jakarta.validation.constraints.NotBlank

data class DeleteRefreshTokenRequest(
    @field:NotBlank val toUsername: String
)