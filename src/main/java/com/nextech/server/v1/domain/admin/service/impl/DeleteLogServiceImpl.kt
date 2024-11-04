package com.nextech.server.v1.domain.admin.service.impl

import com.nextech.server.v1.domain.admin.service.DeleteLogService
import com.nextech.server.v1.global.exception.LogNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class DeleteLogServiceImpl(
    @Value("\${logging.file.path}") private val filePath: String
) : DeleteLogService {
    override fun deleteLog() {
        val logFile = File("$filePath/spring.log")
        if (!logFile.exists()) {
            throw LogNotFoundException("로그 파일이 존재하지 않습니다.")
        }
        logFile.writeText("")
    }
}