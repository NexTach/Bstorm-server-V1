package com.nextech.server.v1.domain.members.entity

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
    ) @field:NotBlank(message = "Member name cannot be blank") val memberName: String,

    @Column(
        name = "email", nullable = false, unique = true
    ) @field:Email(message = "Email should be valid") @field:NotBlank(message = "Email cannot be blank") val email: String,

    @Column(
        name = "password", nullable = false
    ) @field:NotBlank(message = "Password cannot be blank") @field:Size(
        min = 8, max = 100, message = "Password must be between 8 and 100 characters"
    ) val password: String,

    @Column(name = "age", nullable = false) @field:Min(
        value = 0, message = "Age must be greater than or equal to 0"
    ) val age: Int,

    @Convert(converter = GenderConverter::class) @Column(name = "gender", nullable = false) val gender: Gender,

    @Enumerated(EnumType.STRING) @Column(name = "role", nullable = false) val role: Roles,

    @Column(name = "extent_of_dementia", nullable = false) @field:Min(
        value = 0, message = "Extent of dementia must be greater than or equal to 0"
    ) @field:Max(
        value = 100, message = "Extent of dementia must be less than or equal to 100"
    ) val extentOfDementia: Int = 0,

    @Column(name = "profile_picture_URI", nullable = true, length = 2048) val profilePictureURI: String? = ""
)