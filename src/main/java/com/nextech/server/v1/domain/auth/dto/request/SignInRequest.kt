package com.nextech.server.v1.domain.auth.dto.request

data class SignInRequest(
    val phoneNumber: String,
    val password: String
)