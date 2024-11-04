package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.dto.response.LogResponse
import com.nextech.server.v1.domain.admin.service.GetLogService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class GetLogServiceImpl(@Value("\${logging.file.path}") val filePath: String) : GetLogService {

    override fun getLog(): List<LogResponse> {
        val logEntries = readLogEntries()
        return logEntries
    }

    private fun parseLogLine(line: String): LogResponse? {
        val regex =
            """(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d+\+\d{2}:\d{2})\s+(\w+)\s+(\d+)\s+---\s+\[.*?]\s+(.*)""".toRegex()
        val matchResult = regex.find(line)
        return if (matchResult != null) {
            val (timestamp, level, processId, message) = matchResult.destructured
            LogResponse(timestamp, level, processId, message)
        } else {
            null
        }
    }

    private fun readLogEntries(): List<LogResponse> {
        return File(filePath + "/spring.log").readLines().mapNotNull { parseLogLine(it) }
    }
}