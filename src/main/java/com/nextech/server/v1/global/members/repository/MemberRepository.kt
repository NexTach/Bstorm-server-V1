package com.nextech.server.v1.global.members.repository

import com.nextech.server.v1.domain.members.entity.Members
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Members, Long> {
    fun findByPhoneNumber(phoneNumber: String): Members?
}