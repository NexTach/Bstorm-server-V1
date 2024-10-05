package com.nextech.server.v1.global.security.auth

import com.nextech.server.v1.domain.members.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val member = userRepository.findByEmail(id)
        return member?.let {
            AuthDetails(it)
        } ?: throw UsernameNotFoundException("User not found with id: $id")
    }
}