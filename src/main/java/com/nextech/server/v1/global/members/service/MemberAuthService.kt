package com.nextech.server.v1.global.members.service

import com.nextech.server.v1.domain.members.entity.Members
import com.nextech.server.v1.domain.members.repository.MemberRepository
import com.nextech.server.v1.global.security.jwt.service.JwtAuthenticationService
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberAuthService(
    private val jwtTokenService: JwtTokenService,
    private val jwtAuthenticationService: JwtAuthenticationService,
    private val memberRepository: MemberRepository
) {
    fun getMemberByToken(request: HttpServletRequest): Members {
        val token: String = jwtTokenService.resolveToken(request.getHeader("Authorization"))
        val userInfo = jwtAuthenticationService.getAuthentication(token)
        val member = memberRepository.findByPhoneNumber(userInfo.name)
        member?.let {
            return it
        }
        throw UsernameNotFoundException("User not found")
    }
}