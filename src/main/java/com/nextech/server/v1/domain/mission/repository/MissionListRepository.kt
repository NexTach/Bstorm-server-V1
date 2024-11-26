package com.nextech.server.v1.domain.mission.repository

import com.nextech.server.v1.domain.mission.entity.MissionList
import com.nextech.server.v1.global.members.entity.Members
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MissionListRepository : JpaRepository<MissionList, Long>{
    fun findByMember(member: Members): List<MissionList>
}