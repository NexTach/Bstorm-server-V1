package com.nextech.server.v1.global.members.dto.response

import com.nextech.server.v1.domain.members.dto.response.MembersInquiryResponse

data class MembersInquiryListResponse(
    val members: List<MembersInquiryResponse>
)