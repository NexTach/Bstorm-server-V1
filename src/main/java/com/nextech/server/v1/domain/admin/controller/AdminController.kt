package com.nextech.server.v1.domain.admin.controller

import com.nextech.server.v1.domain.admin.dto.request.DeleteRefreshTokenRequest
import com.nextech.server.v1.domain.admin.dto.response.LogResponse
import com.nextech.server.v1.domain.admin.service.*
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val getRefreshTokenService: GetRefreshTokenService,
    private val deleteRefreshTokenService: DeleteRefreshTokenService,
    private val allDeleteRefreshTokenService: AllDeleteRefreshTokenService,
    private val getLogService: GetLogService,
    private val deleteLogService: DeleteLogService
) {
    @GetMapping("/refreshtoken")
    fun refreshToken(): List<RefreshToken> {
        return getRefreshTokenService.getRefreshToken()
    }

    @DeleteMapping("/refreshtoken")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRefreshToken(@RequestBody toUser: DeleteRefreshTokenRequest) {
        deleteRefreshTokenService.deleteRefreshToken(toUser)
    }

    @DeleteMapping("/refreshtoken/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun allDeleteRefreshToken() {
        allDeleteRefreshTokenService.allDeleteRefreshToken()
    }

    @GetMapping("/log")
    fun getLog(): List<LogResponse> {
        return getLogService.getLog()
    }

    @DeleteMapping("/log")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLog() {
        deleteLogService.deleteLog()
    }
}