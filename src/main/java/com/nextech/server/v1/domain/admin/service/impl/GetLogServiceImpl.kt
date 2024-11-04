package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.dto.response.LogResponse
import com.nextech.server.v1.domain.admin.service.GetLogService
import com.nextech.server.v1.global.exception.LogNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class GetLogServiceImpl(@Value("\${logging.file.path}") val filePath: String) : GetLogService {

    override fun getLog(): List<LogResponse> {
        val logEntries = readLogEntries()
        return logEntries
    }

    private fun parseLogLine(line: String): LogResponse {
        val regex =
            """(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d+\+\d{2}:\d{2})\s+(\w+)\s+(\d+)\s+---\s+\[.*?]\s+(.*)""".toRegex()
        val matchResult = regex.find(line)
        return if (matchResult != null) {
            val (timestamp, level, processId, message) = matchResult.destructured
            LogResponse(timestamp, level, processId, message)
        } else {
            throw LogNotFoundException("Log parsing failed")
        }
    }

    private fun readLogEntries(): List<LogResponse> {
        val logFile = File("$filePath/spring.log")
        if (!logFile.exists() || logFile.readLines().isEmpty()) {
            throw LogNotFoundException("log file not found")
        }
        return logFile.readLines().map { parseLogLine(it) }
    }
}