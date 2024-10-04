package com.nextech.server.v1.domain.auth.dto.response

import com.nextech.server.v1.global.enums.Gender
import com.nextech.server.v1.global.enums.Roles

data class SignUpResponse(
    val id: Long,
    val memberName: String,
    val email: String,
    val age: Short,
    val gender: Gender,
    val role: Roles,
    val extentOfDementia: Short,
    val profilePictureURI: String?
)