package com.nextech.server.v1.global.security.auth.exception

import com.nextech.server.v1.global.dto.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component


@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        val errorResponse = ErrorResponse(HttpStatus.FORBIDDEN, "No access to this endpoint")

        if (response != null) {
            response.status = HttpServletResponse.SC_FORBIDDEN
        }
        if (response != null) {
            response.contentType = "application/json"
        }
        if (response != null) {
            response.writer.write(
                """
                {
                    "status": "${errorResponse.httpCode}",
                    "message": "${errorResponse.message}"
                }
            """.trimIndent()
            )
        }
    }
}