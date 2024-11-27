package com.nextech.server.v1.global.members.entity

import com.nextech.server.v1.domain.mission.entity.Mission
import com.nextech.server.v1.global.enums.Gender
import com.nextech.server.v1.global.enums.GenderConverter
import com.nextech.server.v1.global.enums.Roles
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "members")
data class Members(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @Column(
        name = "member_name", nullable = false
    ) @field:NotBlank(message = "Member name cannot be blank") var memberName: String,

    @Column(
        name = "phone_number", nullable = false, unique = true
    ) @field:NotBlank(message = "Email cannot be blank") var phoneNumber: String,

    @Column(
        name = "password", nullable = false
    ) @field:NotBlank(message = "Password cannot be blank") @field:Size(
        min = 8, max = 100, message = "Password must be between 8 and 100 characters"
    ) var password: String,

    @Column(name = "age", nullable = false) @field:Min(
        value = 0, message = "Age must be greater than or equal to 0"
    ) var age: Int,

    @Convert(converter = GenderConverter::class) @Column(name = "gender", nullable = false) var gender: Gender,

    @Enumerated(EnumType.STRING) @Column(name = "role", nullable = false) var role: Roles,

    @Column(name = "extent_of_dementia", nullable = false) @field:Min(
        value = 0, message = "Extent of dementia must be greater than or equal to 0"
    ) @field:Max(
        value = 100, message = "Extent of dementia must be less than or equal to 100"
    ) var extentOfDementia: Int = 0,

    @Column(name = "profile_picture_URI", nullable = true, length = 2048) var profilePictureURI: String? = null,

    @Column(name = "profile_picture_name", nullable = true, length = 2048) var profilePictureName: String? = null,

    @OneToMany(mappedBy = "from", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var missions: MutableList<Mission> = mutableListOf(),
) {
    fun addMission(mission: Mission) {
        this.missions.add(mission)
    }
    fun getMission(): List<Mission> = this.missions

    fun getName(): String {
        return this.memberName
    }
}