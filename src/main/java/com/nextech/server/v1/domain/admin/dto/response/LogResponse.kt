package com.nextech.server.v1.domain.admin.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LogResponse(
    val timestamp: String,
    val level: String,
    val processId: String,
    val message: String
)