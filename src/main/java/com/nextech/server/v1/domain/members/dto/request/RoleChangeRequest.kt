package com.nextech.server.v1.domain.members.dto.request

import com.nextech.server.v1.global.enums.Roles

data class RoleChangeRequest(
    val currentPassword: String,
    val role: Roles
)