package com.nextech.server.v1.domain.members.dto.response

import com.nextech.server.v1.global.enums.Gender
import com.nextech.server.v1.global.enums.Roles
import com.nextech.server.v1.global.members.dto.response.MembersInquiryListResponse

data class MembersInquiryResponse(
    val id: Long,
    val memberName: String,
    val age: Short,
    val gender: Gender,
    val role: Roles,
    val extentOfDementia: Int,
    val profilePictureURI: String?,
    val relation: MembersInquiryListResponse?
)