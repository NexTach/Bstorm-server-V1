package com.nextech.server.v1.domain.members.repository

import com.nextech.server.v1.domain.members.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>{
    fun findByEmail(email: String): Member?
}