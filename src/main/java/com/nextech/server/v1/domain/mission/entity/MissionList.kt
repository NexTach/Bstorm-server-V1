package com.nextech.server.v1.domain.mission.entity

import com.nextech.server.v1.global.members.entity.Members
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "mission_lists")
data class MissionList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Members,
    @Column(name = "completed", nullable = false)
    var completions: Int,
    @Column(name = "date", nullable = false)
    var date: LocalDate
)