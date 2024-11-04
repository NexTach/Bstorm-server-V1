package com.nextech.server.v1.domain.admin.service

import com.nextech.server.v1.domain.admin.dto.response.LogResponse

interface GetLogService {
    fun getLog(): List<LogResponse>
}