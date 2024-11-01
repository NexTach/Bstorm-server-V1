package com.nextech.server.v1.domain.admin.controller

import com.nextech.server.v1.domain.admin.service.GetRefreshTokenService
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(private val getRefreshTokenService: GetRefreshTokenService) {
    @GetMapping("/refreshtoken")
    fun refreshToken(): List<RefreshToken> {
        return getRefreshTokenService.getRefreshToken()
    }
}