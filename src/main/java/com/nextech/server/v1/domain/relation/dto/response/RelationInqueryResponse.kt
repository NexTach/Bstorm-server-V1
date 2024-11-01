package com.nextech.server.v1.domain.relation.dto.response

import com.nextech.server.v1.global.members.entity.Members

data class RelationInqueryResponse(
    val relationId: Long?,
    var fromProtector: Members,
    var toWard: List<Members>
)