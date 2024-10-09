package com.nextech.server.v1.domain.members.dto.request

data class PasswordChangeRequest(
    val currentPassword: String,
    val newPassword: String
)