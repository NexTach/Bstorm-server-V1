package com.nextech.server.v1.domain.members.dto.response

import com.nextech.server.v1.domain.members.entity.Members

data class AllMembersInquiryResponse (
    val members: List<Members>
)