package com.nextech.server.v1.domain.mission.service.impl

import com.nextech.server.v1.domain.mission.entity.MissionList
import com.nextech.server.v1.domain.mission.repository.MissionListRepository
import com.nextech.server.v1.domain.mission.service.GetMissionListService
import com.nextech.server.v1.global.exception.MissionListNotFoundException
import com.nextech.server.v1.global.members.service.MemberAuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class GetMissionListServiceImpl(
    private val memberAuthService: MemberAuthService,
    private val missionListRepository: MissionListRepository
) : GetMissionListService {
    override fun getMissionList(requset: HttpServletRequest): List<MissionList> {
        val member = memberAuthService.getMemberByToken(requset)
        val checkList = missionListRepository.findByMember(member)
        if (checkList.isEmpty()) {
            throw MissionListNotFoundException("Mission list not found")
        }
        return checkList
    }
}