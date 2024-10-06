package com.nextech.server.v1.domain.auth.dto.request

import com.nextech.server.v1.global.enums.Gender
import com.nextech.server.v1.global.enums.Roles
import com.nextech.server.v1.global.enums.SignUpRequestRoles
import jakarta.validation.constraints.*

data class SignUpRequest(
    @field:NotBlank @field:Pattern(regexp = "^[a-zA-Z0-9_\\-!+=*.,\"']+$") val memberName: String,
    @field:PositiveOrZero @field:NotNull @field:Max(165) @field:Min(0) val age: Short,
    @field:NotBlank val password: String,
    @field:NotBlank val phoneNumber: String,
    @field:NotBlank val gender: Gender,
    @field:NotBlank @field:Pattern(regexp = "ROLE_WARD|ROLE_PROTECTOR", message = "Invalid role") val role: SignUpRequestRoles,
    @field:Size(min = 0, max = 100) val extentOfDementia: Short
)