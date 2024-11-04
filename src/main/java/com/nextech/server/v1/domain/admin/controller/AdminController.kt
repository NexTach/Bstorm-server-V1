package com.nextech.server.v1.domain.admin.controller

import com.nextech.server.v1.domain.admin.dto.request.DeleteRefreshTokenRequest
import com.nextech.server.v1.domain.admin.dto.response.LogResponse
import com.nextech.server.v1.domain.admin.service.AllDeleteRefreshTokenService
import com.nextech.server.v1.domain.admin.service.DeleteRefreshTokenService
import com.nextech.server.v1.domain.admin.service.GetLogService
import com.nextech.server.v1.domain.admin.service.GetRefreshTokenService
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val getRefreshTokenService: GetRefreshTokenService,
    private val deleteRefreshTokenService: DeleteRefreshTokenService,
    private val allDeleteRefreshTokenService: AllDeleteRefreshTokenService,
    private val getLogService: GetLogService
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
}