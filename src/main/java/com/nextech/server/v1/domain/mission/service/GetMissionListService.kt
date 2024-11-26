package com.nextech.server.v1.domain.mission.service

import com.nextech.server.v1.domain.mission.entity.MissionList
import jakarta.servlet.http.HttpServletRequest

interface GetMissionListService {
    fun getMissionList(requset:HttpServletRequest): List<MissionList>
}