package com.nextech.server.v1.domain.members.dto.request

data class PhoneNumberChangeRequest(
    val currentPassword: String,
    val newPhoneNumber: String
)